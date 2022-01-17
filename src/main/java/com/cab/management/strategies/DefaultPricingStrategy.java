package com.cab.management.strategies;

import com.cab.management.model.Location;
import org.springframework.stereotype.Service;

@Service
public class DefaultPricingStrategy implements PricingStrategy {

  public static final Double PER_KM_RATE = 10.0;

  @Override
  public Double findPrice(Location fromPoint, Location toPoint) {
    return fromPoint.distance(toPoint) * PER_KM_RATE;
  }
}
