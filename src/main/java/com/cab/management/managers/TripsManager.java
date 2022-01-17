package com.cab.management.managers;


import com.cab.management.exceptions.NoCabsAvailableException;
import com.cab.management.exceptions.TripNotFoundException;
import com.cab.management.model.Cab;
import com.cab.management.model.Location;
import com.cab.management.model.Rider;
import com.cab.management.model.Trip;
import com.cab.management.strategies.CabMatchingStrategy;
import com.cab.management.strategies.PricingStrategy;
import lombok.Builder;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Builder
public class TripsManager {

  public static final Double MAX_ALLOWED_TRIP_MATCHING_DISTANCE = 10.0;
  private final Map<String, List<Trip>> trips = new HashMap<>();

  private final CabsManager cabsManager;
  private final CabMatchingStrategy cabMatchingStrategy;
  private final PricingStrategy pricingStrategy;

  public void createTrip(
      @NonNull final Rider rider,
      @NonNull final Location fromPoint,
      @NonNull final Location toPoint) {
    final List<Cab> closeByCabs =
        cabsManager.getCabs(fromPoint, MAX_ALLOWED_TRIP_MATCHING_DISTANCE);
    final List<Cab> closeByAvailableCabs =
        closeByCabs.stream()
            .filter(cab -> cab.getCurrentTrip() == null)
            .collect(Collectors.toList());

    final Cab selectedCab =
        cabMatchingStrategy.matchCabToRider(rider, closeByAvailableCabs, fromPoint, toPoint);
    if (selectedCab == null) {
      throw new NoCabsAvailableException();
    }

    final Double price = pricingStrategy.findPrice(fromPoint, toPoint);
    final Trip newTrip = new Trip(rider, selectedCab, price, fromPoint, toPoint);
    if (!trips.containsKey(rider.getId())) {
      trips.put(rider.getId(), new ArrayList<>());
    }
    trips.get(rider.getId()).add(newTrip);
    selectedCab.setCurrentTrip(newTrip);
  }

  public List<Trip> tripHistory(@NonNull final Rider rider) {
    return trips.get(rider.getId());
  }

  public void endTrip(@NonNull final Cab cab) {
    if (cab.getCurrentTrip() == null) {
      throw new TripNotFoundException();
    }

    cab.getCurrentTrip().endTrip();
    cab.setCurrentTrip(null);

    //Call cabsManager.updateCabAvailability if want to make cab is available as soon as trip ends.

  }
}
