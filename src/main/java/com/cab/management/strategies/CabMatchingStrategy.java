package com.cab.management.strategies;


import com.cab.management.model.Cab;
import com.cab.management.model.Location;
import com.cab.management.model.Rider;

import java.util.List;

public interface CabMatchingStrategy {

  Cab matchCabToRider(Rider rider, List<Cab> candidateCabs, Location fromPoint, Location toPoint);
}
