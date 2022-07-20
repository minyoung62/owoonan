package com.owoonan.owoonan.domain.record.dto.chart;

import com.owoonan.owoonan.domain.workout.domain.vo.WorkoutPart;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LineChartSearch {

    @NotNull
    private WorkoutPart workoutPart;

    private String workout;

    private Integer offset=0;

    private Integer limit=7;
}
