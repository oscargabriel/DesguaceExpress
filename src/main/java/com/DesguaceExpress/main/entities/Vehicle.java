package com.DesguaceExpress.main.entities;

import jakarta.persistence.*;
import lombok.Builder;


import java.io.Serializable;
import java.util.Date;

@Entity
@Builder
@Table(name = "vehicle")
public class Vehicle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "license_plate", nullable = false)
    private String licensePlate;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "make", nullable = false)
    private String make;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "year", nullable = false)
    private String year;

    @Column(name = "create_on", nullable = true)
    private Date createOn;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "members_id",
            foreignKey = @ForeignKey(name = "fk_vehicle_members"))
    private Members membersId;


    public Vehicle() {
    }

    private Vehicle(Long id, String licensePlate, String type, String make, String model, String year, Date createOn, Members membersId) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.type = type;
        this.make = make;
        this.model = model;
        this.year = year;
        this.createOn = createOn;
        this.membersId = membersId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String brand) {
        this.make = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Date getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    public Members getMembersId() {
        return membersId;
    }

    public void setMembersId(Members associateId) {
        this.membersId = associateId;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", licensePlate='" + licensePlate + '\'' +
                ", type='" + type + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year='" + year + '\'' +
                ", createOn=" + createOn +
                ", membersId=" + membersId +
                '}';
    }
}
