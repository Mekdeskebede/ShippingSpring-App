package com.bootcamp.deliver.Service;

import java.util.Optional;

import com.bootcamp.deliver.Model.GeoLocation;

public interface GeoLocationService {
    Optional<GeoLocation> computeGeoLocation(String fullAddressLine);
}