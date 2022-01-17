package com.cab.management.strategies;


import com.cab.management.model.Location;

public interface PricingStrategy {
  Double findPrice(Location fromPoint, Location toPoint);
}
