package com.uber.driver_service.service.impl;

import com.uber.driver_service.service.DriverService;
import com.uber.rider_service.EnumConstants;
import com.uber.rider_service.dto.RideEventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private KafkaTemplate<String, RideEventDTO> kafkaTemplate;

    @Override
    @KafkaListener(topics = "ride-requested",groupId = "driver-group")
    public void driverAssignment(RideEventDTO rideEventDTO) {
        rideEventDTO.setDriverId((long) (Math.random() * 100));
        rideEventDTO.setStatus(EnumConstants.RideStatus.DRIVER_ASSIGNED);
        kafkaTemplate.send("driver-assigned",rideEventDTO);
    }
}
