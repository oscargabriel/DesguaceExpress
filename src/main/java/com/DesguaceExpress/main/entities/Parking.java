package com.DesguaceExpress.main.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Parking implements Serializable {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    String Name;

    Integer MaxCapacity;

    Float Cost_Hour;

    Date CreateOn;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Location_id",
            foreignKey = @ForeignKey(name = "fk_parking_location"))
    private Location locationId;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "members_id",
            foreignKey = @ForeignKey(name = "fk_parking_members"))
    private Members membersId;
}
