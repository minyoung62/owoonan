package com.owoonan.owoonan.domain.record.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DayRecord {
    private LocalDate localDate;
    private List<ResultWorkoutRecordDto> resultWorkoutRecordDtos;

    public DayRecord(LocalDate localDate) {
        this.localDate = localDate;
    }
}
