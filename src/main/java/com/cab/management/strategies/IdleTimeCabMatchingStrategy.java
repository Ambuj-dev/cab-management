package com.cab.management.strategies;


import com.cab.management.model.Cab;
import com.cab.management.model.Location;
import com.cab.management.model.Rider;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@Service
public class IdleTimeCabMatchingStrategy implements CabMatchingStrategy {

  @Override
  public Cab matchCabToRider(
      @NonNull final Rider rider,
      @NonNull final List<Cab> candidateCabs,
      @NonNull final Location fromPoint,
      @NonNull final Location toPoint) {
    if (candidateCabs.isEmpty()) {
      return null;
    }
    return candidateCabs.stream().filter(Cab::getIsAvailable).min(Comparator.comparingLong(Cab::getIdleFrom))
            .get();
  }
}
