package com.owoonan.owoonan.domain.record.dto;


import com.owoonan.owoonan.domain.workout.domain.vo.WorkoutPart;
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
public class ResultWorkoutRecordDto {
    private LocalDate localDate;
    private WorkoutPart workoutPart;
    private List<WorkoutRecordDto> workoutRecordDtos;

    public ResultWorkoutRecordDto(LocalDate localDate, WorkoutPart workoutPart) {
        this.localDate = localDate;
        this.workoutPart = workoutPart;
    }
}

