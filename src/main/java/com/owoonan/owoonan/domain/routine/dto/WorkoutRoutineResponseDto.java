package com.owoonan.owoonan.domain.routine.dto;

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
public class WorkoutRoutineResponseDto {

    private Long routineId;
    private String routineName;
    private List<WorkoutPartDto> workoutParts;
    private List<PartOfWorkout> partOfWorkouts;

    public WorkoutRoutineResponseDto(Long routineId, String routineName) {
        this.routineId = routineId;
        this.routineName = routineName;
    }
}
