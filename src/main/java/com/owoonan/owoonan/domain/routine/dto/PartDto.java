package com.owoonan.owoonan.domain.routine.dto;

import com.owoonan.owoonan.domain.workout.domain.vo.WorkoutPart;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@NoArgsConstructor
public class PartDto {

    private Long routineId;
    private WorkoutPart workoutPart;
    private Boolean isChecked = true;

    public PartDto(Long routineId, WorkoutPart workoutPart) {
        this.routineId = routineId;
        this.workoutPart = workoutPart;
    }

}
