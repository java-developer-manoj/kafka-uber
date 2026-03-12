package com.uber.rider_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RideRequestDTO {

    private long userId;
    private String pickupLocation;
    private String dropLocation;

}
