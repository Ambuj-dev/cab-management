package com.cab.management.controllers;

import com.cab.management.managers.RidersManager;
import com.cab.management.managers.TripsManager;
import com.cab.management.model.Location;
import com.cab.management.model.Rider;
import com.cab.management.model.Trip;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class RidersController {
  private final RidersManager ridersManager;
  private final TripsManager tripsManager;

  @RequestMapping(value = "/register/rider", method = RequestMethod.POST)
  public ResponseEntity registerRider(final String riderId, final String riderName) {
    ridersManager.createRider(new Rider(riderId, riderName));
    return ResponseEntity.ok("");
  }

  @RequestMapping(value = "/book", method = RequestMethod.POST)
  public ResponseEntity book(
      final String riderId,
      final Double sourceX,
      final Double sourceY,
      final Double destX,
      final Double destY) {

    tripsManager.createTrip(
        ridersManager.getRider(riderId),
        new Location(sourceX, sourceY),
        new Location(destX, destY));

    return ResponseEntity.ok("");
  }

  @RequestMapping(value = "/book", method = RequestMethod.GET)
  public ResponseEntity fetchHistory(final String riderId) {
    List<Trip> trips = tripsManager.tripHistory(ridersManager.getRider(riderId));
    return ResponseEntity.ok(trips);
  }
}
