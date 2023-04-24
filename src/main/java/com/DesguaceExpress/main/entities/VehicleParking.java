package com.DesguaceExpress.main.entities;

import jakarta.persistence.*;/*
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;*/
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "vehicle_parking")
public class VehicleParking implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entry", nullable = false)
    @CreationTimestamp
    private LocalDateTime entry;

    @Column(name = "exit", nullable = true)
    private LocalDateTime exit;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Vehicle_Id",
            foreignKey = @ForeignKey(name = "fk_vehicle_vehicleparking"))
    private Vehicle vehicleId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Parking_Id",
            foreignKey = @ForeignKey(name = "fk_parking_vehicleparking"))
    private Parking parkingId;

    @PrePersist
    private void prePersist(){
        this.entry= LocalDateTime.now();
    }

}
