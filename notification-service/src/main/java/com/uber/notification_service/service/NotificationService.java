package com.uber.notification_service.service;


import com.uber.rider_service.dto.RideEventDTO;

public interface NotificationService {
    void rideRequest(RideEventDTO rideEventDTO);
    void driverAssigned(RideEventDTO rideEventDTO);
}
