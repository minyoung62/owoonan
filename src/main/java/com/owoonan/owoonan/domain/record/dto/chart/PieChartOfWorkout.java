package com.owoonan.owoonan.domain.record.dto.chart;


import com.owoonan.owoonan.domain.workout.domain.vo.WorkoutPart;
import lombok.Data;

@Data
public class PieChartOfWorkout {

    private String workoutName;

    private WorkoutPart workoutPart;
    private Double weightOfWorkout;
    private Double ratioOfWorkout;

    public PieChartOfWorkout( String workoutName, WorkoutPart workoutPart,
                              Double weightOfWorkout) {
        this.workoutName = workoutName;
        this.workoutPart = workoutPart;
        this.weightOfWorkout = weightOfWorkout;
    }
}
