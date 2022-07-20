package com.owoonan.owoonan.domain.record.dto.chart;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Volume {
    private LocalDate localDate;
    private Double volume;

    public Volume(LocalDate localDate, Double volume) {
        this.localDate = localDate;
        this.volume = volume;
    }
}
