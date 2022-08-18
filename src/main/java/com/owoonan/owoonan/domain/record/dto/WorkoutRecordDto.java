package com.owoonan.owoonan.domain.record.dto;


import com.owoonan.owoonan.domain.workout.domain.vo.WorkoutPart;
import com.owoonan.owoonan.domain.workout.dto.WorkoutResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkoutRecordDto {
    private LocalDate localDate;
    private Long workoutId;
    private String workoutName;
    private WorkoutPart workoutPart;
    private List<RecordResponseDto> recordResponseDtos;

    public WorkoutRecordDto(LocalDate localDate, Long workoutId, String workoutName, WorkoutPart workoutPart) {
        this.localDate = localDate;
        this.workoutId = workoutId;
        this.workoutName = workoutName;
        this.workoutPart = workoutPart;
    }
}
