package com.owoonan.owoonan.domain.routine.service;

import com.owoonan.owoonan.domain.routine.domain.Routine;
import com.owoonan.owoonan.domain.routine.domain.vo.Part;
import com.owoonan.owoonan.domain.routine.dto.RoutineResponseDto;
import com.owoonan.owoonan.domain.routine.dto.UnCheckParOfWorkout;
import com.owoonan.owoonan.domain.routine.dto.WorkoutRoutineResponseDto;
import com.owoonan.owoonan.domain.routine.error.RoutineMissMatchException;
import com.owoonan.owoonan.domain.routine.error.RoutineNotFoundException;
import com.owoonan.owoonan.domain.routine.repository.RoutineRepository;
import com.owoonan.owoonan.domain.user.domain.User;
import com.owoonan.owoonan.domain.user.error.UserNotFoundException;
import com.owoonan.owoonan.domain.workout.domain.Workout;
import com.owoonan.owoonan.domain.workout.domain.vo.WorkoutPart;
import com.owoonan.owoonan.domain.workout.error.WorkoutMissMatchException;
import com.owoonan.owoonan.domain.workout.repository.WorkoutRepository;
import com.owoonan.owoonan.domain.workoutroutine.domain.WorkoutRoutine;
import com.owoonan.owoonan.global.error.exception.ErrorCode;
import com.owoonan.owoonan.global.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoutineService {

    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;
    private final RoutineRepository routineRepository;

    public Long create(final Routine routine, List<Long> workoutIds, List<WorkoutPart> workoutParts, final String userId) {
        User user = getUser(userId);

        List<Workout> workouts = workoutRepository.findByWorkoutIds(workoutIds);
        if(workouts.size() != workoutIds.size()) throw new WorkoutMissMatchException(ErrorCode.WORKOUT_MISS_MATCH);
        List<WorkoutRoutine> workoutRoutines = WorkoutRoutine.createWorkoutRoutines(workouts, userId);
        List<Part> parts = Part.createParts(workoutParts);

        routine.addParts(parts);
        routine.addWorkoutRoutines(workoutRoutines);
        routine.addUser(user);

        Routine save = routineRepository.save(routine);

        return save.getRoutineId();
    }

    public void update(final Long routineId, final Routine updateRoutine, List<Long> workoutIds, List<WorkoutPart> workoutParts,final String userId) {
        User user = getUser(userId);
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new RoutineNotFoundException(ErrorCode.ROUTINE_NOT_FOUND));
        if (!routine.getUser().getUserId().equals(user.getUserId())) throw new RoutineMissMatchException(ErrorCode.ROUTINE_MISS_MATCH);

        List<Workout> workouts = workoutRepository.findByWorkoutIds(workoutIds);
        List<WorkoutRoutine> workoutRoutines = WorkoutRoutine.createWorkoutRoutines(workouts, userId);
        List<Part> parts = Part.createParts(workoutParts);

        routineRepository.removeParts(routine.getRoutineId());
        routineRepository.removeWorkoutRoutine(routine.getRoutineId());
        routine.patch(updateRoutine, workoutRoutines, parts);

        routineRepository.save(routine);
    }
    @Transactional(readOnly = true)
    public List<RoutineResponseDto> findAll(final String userId) {
        User user = getUser(userId);
        return routineRepository.findRoutineAllByUserId(user.getUserId());
    }

    @Transactional(readOnly = true)
    public WorkoutRoutineResponseDto findOne(final Long routineId, final String userId) {
        Routine routine = routineRepository.findById(routineId).orElseThrow(() -> new RoutineNotFoundException(ErrorCode.ROUTINE_NOT_FOUND));
        if (!routine.getUser().getUserId().equals(userId)) throw new RoutineMissMatchException(ErrorCode.ROUTINE_MISS_MATCH);
        return routineRepository.findWorkoutRoutineResponseDto(routine.getRoutineId());
    }


    @Transactional(readOnly = true)
    public List<UnCheckParOfWorkout> findUncheckedWorkout(final Long routineId, final String userId) {
        Routine routine = routineRepository.findById(routineId).orElseThrow(() -> new RoutineNotFoundException(ErrorCode.ROUTINE_NOT_FOUND));

        if (!routine.getUser().getUserId().equals(userId)) throw new RoutineMissMatchException(ErrorCode.ROUTINE_MISS_MATCH);
        List<Part> parts = routineRepository.findPartById(routine.getRoutineId());
        List<WorkoutPart> workoutParts= new ArrayList<>();
        for(Part part: parts) {
            System.out.println(part.getWorkoutPart());
            workoutParts.add(part.getWorkoutPart());
        }
        List<UnCheckParOfWorkout> uncheckedWorkout = routineRepository.findUncheckedWorkout(routine.getRoutineId(), workoutParts, userId);
        return uncheckedWorkout;
    }

    public void delete(final Long routineId, final String userId) {
        User user = getUser(userId);
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new RoutineNotFoundException(ErrorCode.ROUTINE_NOT_FOUND));
        if (!routine.getUser().getUserId().equals(userId)) {
            throw new RoutineMissMatchException(ErrorCode.ROUTINE_MISS_MATCH);
        }

        routineRepository.delete(routine);
    }
    private User getUser(String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        return user;
    }



}
