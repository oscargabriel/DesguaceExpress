package com.DesguaceExpress.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberToParking {

    private Long membersId;

    private Long parkingId;
}
