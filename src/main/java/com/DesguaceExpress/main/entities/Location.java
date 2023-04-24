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


import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "location")
public class Location implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "departament", nullable = false)
    private String departament;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "ubication", nullable = false)
    private String ubication;
}