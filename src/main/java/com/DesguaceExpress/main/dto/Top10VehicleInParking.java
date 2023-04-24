package com.DesguaceExpress.main.dto;


import jakarta.persistence.Entity;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * clase para la estructura de los datos que se muestran de los 10 vehiculos que mas han registrado entradas
 * en los diferentes paqueaderos
 */

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
