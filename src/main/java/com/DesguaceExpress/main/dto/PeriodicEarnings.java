package com.DesguaceExpress.main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PeriodicEarnings {

    public Double day;

    public Double week;

    public Double month;

    public Double year;
}
