package com.owoonan.owoonan.domain.workout.repository;

import com.owoonan.owoonan.domain.workout.domain.Workout;
import com.owoonan.owoonan.domain.workout.domain.vo.WorkoutPart;
import com.owoonan.owoonan.domain.workout.dto.WorkoutResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.owoonan.owoonan.domain.workout.domain.QWorkout.workout;

@RequiredArgsConstructor
public class WorkoutRepositoryImpl implements  WorkoutRepositoryCustom{

    private final JPAQueryFactory query;

    @Override
    public List<WorkoutResponseDto> findByPart(WorkoutPart workoutPart, String userId) {
        return query
                .select(Projections.fields(WorkoutResponseDto.class,
                                workout.workoutId,
                                workout.workoutName))
                .from(workout)
                .where(workout.workoutPart.eq(workoutPart), workout.user.userId.eq(userId))
                .fetch();
    }
    @Override
    public Workout findByWorkoutName(String workoutName) {
        return query
                .select(workout)
                .from(workout)
                .where(workout.workoutName.eq(workoutName))
                .fetchOne();
    }

}
