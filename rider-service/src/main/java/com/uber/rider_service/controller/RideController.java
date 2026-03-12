package com.uber.rider_service.controller;

import com.uber.rider_service.dto.RideRequestDTO;
import com.uber.rider_service.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RideController {

    @Autowired
    private RideService service;

    @PostMapping("/rideRequest")
    public ResponseEntity<?> rideRequest(@RequestBody RideRequestDTO rideRequestDTO){
        service.rideRequest(rideRequestDTO);
        return null;
    }
}
