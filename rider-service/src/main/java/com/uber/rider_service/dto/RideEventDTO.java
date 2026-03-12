package com.uber.rider_service.dto;

import com.uber.rider_service.EnumConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RideEventDTO {
    private  Long rideId;
    private  Long userId;
    private Long  driverId;
    private String pickupLocation;
    private  String dropLocation;
    private EnumConstants.RideStatus status;
}
