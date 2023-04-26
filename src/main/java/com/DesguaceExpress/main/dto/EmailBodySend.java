package com.DesguaceExpress.main.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class EmailBodySend {

    private String email;

    private String placa;

    private String mensaje;

    private String parqueaderoNombre;

}
