package com.DesguaceExpress.main.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class PartialData {

    private Long parkingId;

    private String partialLicensePlate;

    private String dateInit;

    private String dateEnd;
}
