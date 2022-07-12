package com.owoonan.owoonan.domain.routine.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.owoonan.owoonan.domain.workout.domain.vo.WorkoutPart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UnCheckParOfWorkout {

    private WorkoutPart workoutPart;
    private List<UnCheckedWorkoutDto> unCheckedWorkoutDto;

    public UnCheckParOfWorkout(WorkoutPart wp) {
        this.workoutPart = wp;
    }
}
