package com.uber.notification_service.service.impl;

import com.uber.notification_service.service.NotificationService;
import com.uber.rider_service.dto.RideEventDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    @KafkaListener(topics = "ride-requested",groupId = "notification-group")
    public void rideRequest(RideEventDTO rideEventDTO) {

        System.out.println("Notification: Searching for driver...");
    }

    @Override
    @KafkaListener(topics = "driver-assigned",groupId = "notification-service")
    public void driverAssigned(RideEventDTO rideEventDTO) {
        System.out.println("Notification: Driver assigned for ride " + rideEventDTO.getRideId());
    }
}
