package com.DesguaceExpress.main.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.envers.Audited;
//import lombok.Builder;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@Audited
@Table(name = "vehicle", uniqueConstraints = {
        @UniqueConstraint(name = "uk_license_plate", columnNames = "license_plate")

})
public class Vehicle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "make", nullable = false)
    private String make;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "year", nullable = false)
    private Integer year;

    @CreationTimestamp
    @Column(name = "create_on", nullable = true)
    private LocalDateTime createOn;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "members_id",
            foreignKey = @ForeignKey(name = "fk_vehicle_members"))
    private Members membersId;


    public Vehicle() {
    }

    private Vehicle(Long id, String licensePlate, String type, String make, String model, Integer year, Members membersId) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.type = type;
        this.make = make;
        this.model = model;
        this.year = year;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public LocalDateTime getCreateOn() {
        return createOn;
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
