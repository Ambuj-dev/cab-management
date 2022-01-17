package com.cab.management.managers;

import com.cab.management.exceptions.CabAlreadyExistsException;
import com.cab.management.exceptions.CabNotFoundException;
import com.cab.management.model.Cab;
import com.cab.management.model.Location;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CabsManager {

  Map<String, Cab> cabs = new HashMap<>();

  public void createCab(@NonNull final Cab newCab) {
    if (cabs.containsKey(newCab.getId())) {
      throw new CabAlreadyExistsException();
    }

    cabs.put(newCab.getId(), newCab);
  }

  public Cab getCab(@NonNull final String cabId) {
    if (!cabs.containsKey(cabId)) {
      throw new CabNotFoundException();
    }
    return cabs.get(cabId);
  }

  public void updateCabLocation(@NonNull final String cabId, @NonNull final Location newLocation) {
    if (!cabs.containsKey(cabId)) {
      throw new CabNotFoundException();
    }
    cabs.get(cabId).setCurrentLocation(newLocation);
  }

  public void updateCabAvailability(
      @NonNull final String cabId, @NonNull final Boolean isAvailable) {
    if (!cabs.containsKey(cabId)) {
      throw new CabNotFoundException();
    }
    Cab cab = cabs.get(cabId);
    cab.setIsAvailable(isAvailable);
    if(isAvailable){
      cab.setIdleFrom(ZonedDateTime.now().toInstant().toEpochMilli());
    }
  }

  public List<Cab> getCabs(@NonNull final Location fromPoint, @NonNull final Double distance) {
    List<Cab> result = new ArrayList<>();
    for (Cab cab : cabs.values()) {
      // TODO: Use epsilon comparison because of double
      if (cab.getIsAvailable() && cab.getCurrentLocation().distance(fromPoint) <= distance) {
        result.add(cab);
      }
    }
    return result;
  }
}
