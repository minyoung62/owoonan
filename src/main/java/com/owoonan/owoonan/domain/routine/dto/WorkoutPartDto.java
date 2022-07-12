package com.owoonan.owoonan.domain.routine.dto;

import com.owoonan.owoonan.domain.workout.domain.Workout;
import com.owoonan.owoonan.domain.workout.domain.vo.WorkoutPart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class WorkoutPartDto {
    private WorkoutPart workoutPart;

    public WorkoutPartDto(WorkoutPart wp) {
        this.workoutPart = wp;
    }
}
