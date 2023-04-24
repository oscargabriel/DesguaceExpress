package com.DesguaceExpress.main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VehicleByParking {

    private Long id;

    private String licensePlate;

    private String type;

    private String make;

    private String model;

    private Integer year;

    public String owner;

    public String document;

    private LocalDateTime entry;
}
