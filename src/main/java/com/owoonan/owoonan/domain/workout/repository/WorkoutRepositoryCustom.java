package com.owoonan.owoonan.domain.workout.repository;

import com.owoonan.owoonan.domain.workout.domain.Workout;
import com.owoonan.owoonan.domain.workout.domain.vo.WorkoutPart;
import com.owoonan.owoonan.domain.workout.dto.WorkoutResponseDto;

import java.util.List;

public interface WorkoutRepositoryCustom {

    List<WorkoutResponseDto> findByPart(WorkoutPart workoutPart, String userId);

    Workout findByWorkoutName(String workoutName);
}
