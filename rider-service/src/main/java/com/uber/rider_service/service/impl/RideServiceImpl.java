package com.uber.rider_service.service.impl;

import com.uber.rider_service.EnumConstants;
import com.uber.rider_service.dto.RideEventDTO;
import com.uber.rider_service.dto.RideRequestDTO;
import com.uber.rider_service.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class RideServiceImpl implements RideService {


   @Autowired
   private  KafkaTemplate<String, RideEventDTO> kafkaTemplate;

    @Override
    public void rideRequest(RideRequestDTO rideRequestDTO) {

        final RideEventDTO rideEventDTO = RideEventDTO.builder()
                .rideId(System.currentTimeMillis())
                .userId(rideRequestDTO.getUserId())
                .pickupLocation(rideRequestDTO.getPickupLocation())
                .dropLocation(rideRequestDTO.getDropLocation())
                .status(EnumConstants.RideStatus.RIDE_REQUESTED).build();
        kafkaTemplate.send("ride-requested",rideEventDTO);
    }

    @KafkaListener(topics = "driver-assigned", groupId = "ride-group")
    public void driverAssigned(RideEventDTO event) {

        System.out.println("Driver-Service: "+"Ride Service Updated Status");
        System.out.println("Driver-Service: Ride ID: " + event.getRideId());
        System.out.println("Driver-Service: "+"Driver Assigned: " + event.getDriverId());
    }

}
