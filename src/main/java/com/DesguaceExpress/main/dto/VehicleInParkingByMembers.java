package com.DesguaceExpress.main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VehicleInParkingByMembers {

    private Long id;

    private String licensePlate;

    private LocalDateTime entryParking;

    private HashMap<String, String> parking;
}
