package com.owoonan.owoonan.domain.record.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class RecordResponseDto {

    private LocalDate localDate;
    private Long workoutId;
    private Long recordId;
    private Double weight;
    private Integer rep;

    public RecordResponseDto(LocalDate localDate, Long workoutId, Long recordId, Double weight, Integer rep) {
        this.localDate = localDate;
        this.workoutId = workoutId;
        this.recordId = recordId;
        this.weight = weight;
        this.rep = rep;
    }
}
