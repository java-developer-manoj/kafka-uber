package com.uber.driver_service.service;

import com.uber.rider_service.dto.RideEventDTO;

public interface DriverService {

    void driverAssignment(RideEventDTO rideEventDTO);
}
