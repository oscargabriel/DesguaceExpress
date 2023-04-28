package com.DesguaceExpress.main.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;


import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@Audited
@Table(name = "location")
public class    Location implements Serializable {

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