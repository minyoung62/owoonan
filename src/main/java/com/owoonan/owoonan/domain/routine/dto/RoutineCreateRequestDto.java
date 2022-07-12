package com.owoonan.owoonan.domain.routine.dto;

import com.owoonan.owoonan.domain.routine.domain.Routine;
import com.owoonan.owoonan.domain.workout.domain.vo.WorkoutPart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RoutineCreateRequestDto {

    private String routineName;
    private List<Long> workoutIds;
    private List<WorkoutPart> workoutParts;
    public Routine toEntity() {
        return Routine.builder()
                .routineName(routineName)
                .build();
    }
}
