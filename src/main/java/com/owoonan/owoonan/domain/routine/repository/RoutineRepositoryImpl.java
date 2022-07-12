package com.owoonan.owoonan.domain.routine.repository;

import com.owoonan.owoonan.domain.routine.domain.Routine;
import com.owoonan.owoonan.domain.routine.domain.vo.Part;
import com.owoonan.owoonan.domain.routine.dto.*;
import com.owoonan.owoonan.domain.workout.domain.QWorkout;
import com.owoonan.owoonan.domain.workout.domain.vo.WorkoutPart;
import com.owoonan.owoonan.domain.workoutroutine.domain.QWorkoutRoutine;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.owoonan.owoonan.domain.routine.domain.QRoutine.routine;
import static com.owoonan.owoonan.domain.routine.domain.vo.QPart.part;
import static com.owoonan.owoonan.domain.workout.domain.QWorkout.workout;
import static com.owoonan.owoonan.domain.workoutroutine.domain.QWorkoutRoutine.workoutRoutine;

@RequiredArgsConstructor
public class RoutineRepositoryImpl implements RoutineRepositoryCustom{

    private final JPAQueryFactory query;

    @Override
    public List<RoutineResponseDto> findRoutineAllByUserId(final String userId) {
        List<RoutineResponseDto> result = query.select(Projections.constructor(RoutineResponseDto.class,
                        routine.routineId, routine.routineName))
                .from(routine)
                .where(routine.user.userId.eq(userId))
                .fetch();

        Map<Long, List<PartDto>> routinePartMap = findRoutinePart(toRoutineIds(result));

        result.forEach(r -> r.setPartDtos(routinePartMap.get(r.getRoutineId())));
        return result;
    }

    private Map<Long, List<PartDto>> findRoutinePart(List<Long> routineIds) {
        List<PartDto> result = query.select(Projections.constructor(PartDto.class, routine.routineId, part.workoutPart))
                .from(part)
                .join(part.routine, routine)
                .where(part.routine.routineId.in(routineIds))
                .fetch();

        return  result.stream()
                .collect(Collectors.groupingBy(PartDto::getRoutineId));
    }

    private List<Long> toRoutineIds(List<RoutineResponseDto> result) {
        return result.stream()
                .map(r -> r.getRoutineId())
                .collect(Collectors.toList());
    }

    public WorkoutRoutineResponseDto findWorkoutRoutineResponseDto(Long routineId) {
        WorkoutRoutineResponseDto result = query.select(Projections.constructor(WorkoutRoutineResponseDto.class,
                        routine.routineId, routine.routineName))
                .from(routine)
                .where(routine.routineId.eq(routineId))
                .fetchOne();

        List<WorkoutPartDto> workoutPartDtos = query.select(Projections.constructor(WorkoutPartDto.class, part.workoutPart))
                .from(part)
                .join(part.routine, routine)
                .where(part.routine.routineId.eq(routineId))
                .fetch();

        result.setWorkoutParts(workoutPartDtos);

        List<PartOfWorkout> partOfWorkouts = new ArrayList<>();
        for(WorkoutPartDto wpd : workoutPartDtos) {
            partOfWorkouts.add(new PartOfWorkout(wpd.getWorkoutPart()));
        }


        for(PartOfWorkout pow : partOfWorkouts) {
            List<WorkoutDto> workoutDtos = query.select(Projections.constructor(WorkoutDto.class, workout.workoutId, workout.workoutName))
                    .from(workoutRoutine)
                    .join(workoutRoutine.workout, workout)
                    .where(workoutRoutine.routine.routineId.eq(routineId), workout.workoutPart.eq(pow.getWorkoutPart()))
                    .fetch();

            pow.setWorkoutDtos(workoutDtos);
        }

        result.setPartOfWorkouts(partOfWorkouts);

        return result;
    }

    public List<Part> findPartById(Long routineId) {
        return query.select(part)
                .from(part)
                .join(part.routine, routine)
                .where(part.routine.routineId.eq(routineId))
                .fetch();
    }

    public List<UnCheckParOfWorkout> findUncheckedWorkout(Long routineId, List<WorkoutPart> workoutParts, String userId) {
        List<UnCheckParOfWorkout> unCheckParOfWorkouts = new ArrayList<>();
        for(WorkoutPart workoutPart: workoutParts) {
            List<UnCheckedWorkoutDto> unCheckedWorkoutDtos = query.select(Projections.constructor(UnCheckedWorkoutDto.class, workout.workoutId, workout.workoutName))
                    .from(workout)
                    .leftJoin(workout.workoutRoutines, workoutRoutine)
                    .where(workout.workoutPart.eq(workoutPart).and(workoutRoutine.routine.routineId.isNull()).and(workout.user.userId.eq(userId)))
                    .fetch();

            unCheckParOfWorkouts.add(new UnCheckParOfWorkout(workoutPart, unCheckedWorkoutDtos));
        }

        return unCheckParOfWorkouts;
    }

    public void removeParts(Long routineId) {
        long execute = query
                .delete(part)
                .where(part.routine.routineId.eq(routineId))
                .execute();
    }

    public void removeWorkoutRoutine(Long routineId) {
        long execute = query
                .delete(workoutRoutine)
                .where(workoutRoutine.routine.routineId.eq(routineId))
                .execute();
    }


}
