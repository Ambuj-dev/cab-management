package com.cab.management.managers;

import com.cab.management.exceptions.RiderAlreadyExistsException;
import com.cab.management.exceptions.RiderNotFoundException;
import com.cab.management.model.Rider;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/** In memory database for storing jobs. */
@Service
public class RidersManager {
  Map<String, Rider> riders = new HashMap<>();

  public void createRider(@NonNull final Rider newRider) {
    if (riders.containsKey(newRider.getId())) {
      throw new RiderAlreadyExistsException();
    }

    riders.put(newRider.getId(), newRider);
  }

  public Rider getRider(@NonNull final String riderId) {
    if (!riders.containsKey(riderId)) {
      throw new RiderNotFoundException();
    }
    return riders.get(riderId);
  }
}
