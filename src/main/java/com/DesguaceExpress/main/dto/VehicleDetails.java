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
public class VehicleDetails {

    private Long id;

    private String licensePlate;

    private LocalDateTime entry;

    private HashMap<String, String> parking;

    private Boolean previous;
}
