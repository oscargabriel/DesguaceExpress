package com.DesguaceExpress.main.entities;

import jakarta.persistence.*;
import lombok.Builder;


import java.io.Serializable;
import java.util.Date;

@Entity
@Builder
@Table(name = "parking")
public class Parking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "max_capacity", nullable = false)
    private Integer maxCapacity;

    @Column(name = "cost_hour", nullable = false)
    private Float costHour;

    @Column(name = "create_on", nullable = true)
    private Date createOn;

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

    private Parking(Long id, String name, Integer maxCapacity, Float costHour, Date createOn, Location locationId, Members membersId) {
        this.id = id;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.costHour = costHour;
        this.createOn = createOn;
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

    public Date getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
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

    @Override
    public String toString() {
        return "Parking{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maxCapacity=" + maxCapacity +
                ", costHour=" + costHour +
                ", createOn=" + createOn +
                ", locationId=" + locationId +
                ", membersId=" + membersId +
                '}';
    }
}
