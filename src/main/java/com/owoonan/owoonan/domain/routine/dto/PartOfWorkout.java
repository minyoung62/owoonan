package com.owoonan.owoonan.domain.routine.dto;

import com.owoonan.owoonan.domain.workout.domain.Workout;
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
public class PartOfWorkout {

    private WorkoutPart workoutPart;
    private List<WorkoutDto> workoutDtos;

    public PartOfWorkout(WorkoutPart wp) {
        this.workoutPart = wp;
    }
}
