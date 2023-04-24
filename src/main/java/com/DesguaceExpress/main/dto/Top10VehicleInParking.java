package com.DesguaceExpress.main.dto;


import jakarta.persistence.Entity;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


import java.io.Serializable;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class Top10VehicleInParking {

    public Long id;

    public Long count;

    public String licensePlate;

    public String type;

    public String make;

    public String model;

    public String owner;

    public String document;


}
