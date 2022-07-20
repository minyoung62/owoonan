package com.owoonan.owoonan.domain.record.dto.chart;

import com.owoonan.owoonan.domain.workout.domain.vo.WorkoutPart;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PieChartOfWorkoutPart {

    private WorkoutPart workoutPart;
    private Double weightOfWorkoutPart;
    private Double ratioOfWorkoutPart;

    private List<PieChartOfWorkout> pieChartOfWorkouts = new ArrayList<>();


    public PieChartOfWorkoutPart(WorkoutPart workoutPart, Double weightOfWorkoutPart, Double ratioOfWorkoutPart) {
        this.workoutPart = workoutPart;
        this.weightOfWorkoutPart = weightOfWorkoutPart;
        this.ratioOfWorkoutPart = ratioOfWorkoutPart;
    }
}
