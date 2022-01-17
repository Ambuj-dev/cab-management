package com.cab.management.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
public class Cab {
  private String id;
  private String driverName;

  @Setter private Trip currentTrip;
  @Setter private Location currentLocation;
  @Setter private Boolean isAvailable;
  @Setter private Long idleFrom;


  public Cab(String id, String driverName) {
    this.id = id;
    this.driverName = driverName;
    this.isAvailable = true;
    this.idleFrom = ZonedDateTime.now().toInstant().toEpochMilli();
  }

  @Override
  public String toString() {
    return "Cab{" +
        "id='" + id + '\'' +
        ", driverName='" + driverName + '\'' +
        ", currentLocation=" + currentLocation +
        ", isAvailable=" + isAvailable +
        '}';
  }
}
