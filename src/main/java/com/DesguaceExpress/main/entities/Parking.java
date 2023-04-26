package com.DesguaceExpress.main.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
//import lombok.Builder;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@Table(name = "parking")
public class Parking implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "current_capacity", nullable = false)
    private Integer currentCapacity;

    @Column(name = "max_capacity", nullable = false)
    private Integer maxCapacity;

    @Column(name = "cost_hour", nullable = false)
    private Float costHour;

    @CreationTimestamp
    @Column(name = "create_on", nullable = true)
    private LocalDateTime createOn;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "location_id",
            foreignKey = @ForeignKey(name = "fk_parking_location"))
    private Location locationId;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "members_id",
            foreignKey = @ForeignKey(name = "fk_parking_members"))
    private Members membersId;

    public Parking() {
    }

    public Parking(Long id, String name, Integer currentCapacity, Integer maxCapacity, Float costHour, Location locationId, Members membersId) {
        this.id = id;
        this.name = name;
        this.currentCapacity = currentCapacity;
        this.maxCapacity = maxCapacity;
        this.costHour = costHour;
        this.locationId = locationId;
        this.membersId = membersId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(Integer currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Float getCostHour() {
        return costHour;
    }

    public void setCostHour(Float costHour) {
        this.costHour = costHour;
    }

    public LocalDateTime getCreateOn() {
        return createOn;
    }

    public Location getLocationId() {
        return locationId;
    }

    public void setLocationId(Location locationId) {
        this.locationId = locationId;
    }

    public Members getMembersId() {
        return membersId;
    }

    public void setMembersId(Members membersId) {
        this.membersId = membersId;
    }
}
