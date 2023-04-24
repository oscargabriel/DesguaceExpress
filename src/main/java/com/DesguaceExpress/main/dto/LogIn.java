package com.DesguaceExpress.main.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * clase para los datos que llegan por http para el registrar la entrada de vehiculo
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LogIn {

    private String licencePlate;

    private Long idParking;
}
