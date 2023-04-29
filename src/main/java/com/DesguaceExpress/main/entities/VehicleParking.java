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
import org.hibernate.envers.Audited;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Audited
@Table(name = "vehicle_parking")
public class VehicleParking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "entry", nullable = false)
    @CreationTimestamp
    private LocalDateTime entry;

    @Column(name = "exit", nullable = true)
    private LocalDateTime exit;

    @Column(name = "cost", nullable = true)
    private Float cost;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Vehicle_Id",
            foreignKey = @ForeignKey(name = "fk_vehicle_vehicleparking"))
    private Vehicle vehicleId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Parking_Id",
            foreignKey = @ForeignKey(name = "fk_parking_vehicleparking"))
    private Parking parkingId;

    /**
     * asigna la infomacion de ingreso automaticamente al ingresar al parqueadero
     */
    @PrePersist
    private void prePersist(){
        this.entry= LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate(){

    }

}
