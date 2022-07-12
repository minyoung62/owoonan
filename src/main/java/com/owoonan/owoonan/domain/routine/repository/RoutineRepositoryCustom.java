package com.owoonan.owoonan.domain.routine.repository;

import com.owoonan.owoonan.domain.routine.domain.vo.Part;
import com.owoonan.owoonan.domain.routine.dto.RoutineResponseDto;
import com.owoonan.owoonan.domain.routine.dto.UnCheckParOfWorkout;
import com.owoonan.owoonan.domain.routine.dto.WorkoutRoutineResponseDto;
import com.owoonan.owoonan.domain.workout.domain.vo.WorkoutPart;

import java.util.List;

public interface RoutineRepositoryCustom {
    List<RoutineResponseDto> findRoutineAllByUserId(String userId);

    WorkoutRoutineResponseDto findWorkoutRoutineResponseDto(Long routineId);

    List<Part> findPartById(Long routineId);

    List<UnCheckParOfWorkout> findUncheckedWorkout(Long routineId, List<WorkoutPart> workoutParts, String userId);

    void removeParts(Long routineId);

    void removeWorkoutRoutine(Long routineId);
}
