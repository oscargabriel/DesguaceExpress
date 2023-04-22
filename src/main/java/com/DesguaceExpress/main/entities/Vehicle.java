package com.DesguaceExpress.main.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Vehicle implements Serializable {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    String LicensePlate;

    String Type;

    String Brand;

    String Model;

    String Date;

    java.util.Date CreateOn;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "associate_id",
            foreignKey = @ForeignKey(name = "fk_vehicle_members"))
    private Members associateId;


}
