package com.owoonan.owoonan.domain.routine.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class WorkoutDto {
    private Long workoutId;
    private String workoutName;
    private Boolean isChecked = true;

    public WorkoutDto(Long workoutId, String workoutName) {
        this.workoutId = workoutId;
        this.workoutName = workoutName;
    }
}
