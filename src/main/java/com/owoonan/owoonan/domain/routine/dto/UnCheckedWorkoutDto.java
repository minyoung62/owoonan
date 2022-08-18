package com.owoonan.owoonan.domain.routine.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UnCheckedWorkoutDto {

    private Long workoutId;
    private String workoutName;

    public UnCheckedWorkoutDto(Long workoutId, String workoutName) {
        this.workoutId = workoutId;
        this.workoutName = workoutName;
    }
}
