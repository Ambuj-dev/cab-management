package com.cab.management.controllers;

import com.cab.management.managers.CabsManager;
import com.cab.management.managers.TripsManager;
import com.cab.management.model.Cab;
import com.cab.management.model.Location;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CabsController {
  private final CabsManager cabsManager;
  private final TripsManager tripsManager;

  @RequestMapping(value = "/register/cab", method = RequestMethod.POST)
  public ResponseEntity registerCab(final String cabId, final String driverName) {
    cabsManager.createCab(new Cab(cabId, driverName));
    return ResponseEntity.ok("");
  }

  @RequestMapping(value = "/update/cab/location", method = RequestMethod.POST)
  public ResponseEntity updateCabLocation(
      final String cabId, final Double newX, final Double newY) {

    cabsManager.updateCabLocation(cabId, new Location(newX, newY));
    return ResponseEntity.ok("");
  }

  @RequestMapping(value = "/update/cab/availability", method = RequestMethod.POST)
  public ResponseEntity updateCabAvailability(final String cabId, final Boolean isAvailable) {
    cabsManager.updateCabAvailability(cabId, isAvailable);
    return ResponseEntity.ok("");
  }

  @RequestMapping(value = "/update/cab/end/trip", method = RequestMethod.POST)
  public ResponseEntity endTrip(final String cabId) {
    tripsManager.endTrip(cabsManager.getCab(cabId));
    return ResponseEntity.ok("");
  }
}
