package com.DesguaceExpress.main.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
public class VehicleParking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Date Entry;

    Date Exit;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Vehicle_Id",
            foreignKey = @ForeignKey(name = "fk_vehicle_vehicleparking"))
    private Vehicle VehicleId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Parking_Id",
            foreignKey = @ForeignKey(name = "fk_parking_vehicleparking"))
    private Parking ParkingId;

}
