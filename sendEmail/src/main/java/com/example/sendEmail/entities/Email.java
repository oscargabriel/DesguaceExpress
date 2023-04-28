package com.example.sendEmail.entities;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class Email {

    private String email;

    private String placa;

    private String mensaje;

    private String parqueaderoNombre;

}
