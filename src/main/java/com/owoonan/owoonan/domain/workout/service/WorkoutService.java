package com.owoonan.owoonan.domain.workout.service;

import com.owoonan.owoonan.domain.user.domain.User;
import com.owoonan.owoonan.domain.user.error.UserNotFoundException;
import com.owoonan.owoonan.domain.workout.domain.Workout;
import com.owoonan.owoonan.domain.workout.domain.vo.WorkoutPart;
import com.owoonan.owoonan.domain.workout.dto.WorkoutResponseDto;
import com.owoonan.owoonan.domain.workout.error.WorkoutMissMatchException;
import com.owoonan.owoonan.domain.workout.error.WorkoutNameDuplicationException;
import com.owoonan.owoonan.domain.workout.error.WorkoutNotFoundException;
import com.owoonan.owoonan.domain.workout.repository.WorkoutRepository;
import com.owoonan.owoonan.global.error.exception.ErrorCode;
import com.owoonan.owoonan.global.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkoutService {

    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;

    public WorkoutResponseDto create(final Workout workout, final String userId) {
        User user = getUser(userId);
        Workout findWorkout = workoutRepository.findByWorkoutName(workout.getWorkoutName());
        if (findWorkout != null ) throw new WorkoutNameDuplicationException(ErrorCode.WORKOUT_NAME_DUPLICATION);
        Workout save = workoutRepository.save(workout.addUser(user));
        return WorkoutResponseDto.of(save);
    }

    @Transactional(readOnly = true)
    public WorkoutResponseDto findById(final Long workoutId) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new WorkoutNotFoundException(ErrorCode.WORKOUT_NOT_FOUND));
        return WorkoutResponseDto.of(workout);
    }

    @Transactional(readOnly = true)
    public List<WorkoutResponseDto> findByPart(final WorkoutPart workoutPart, final String userId) {
        User user = getUser(userId);
        return workoutRepository.findByPart(workoutPart, user.getUserId());
    }

    public void delete(final Long workoutId, final String userId) {
        User user = getUser(userId);
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new WorkoutNotFoundException(ErrorCode.WORKOUT_NOT_FOUND));

        if (workout.getUser().getUserId() != user.getUserId()) throw new WorkoutMissMatchException(ErrorCode.WORKOUT_MISS_MATCH);
        workoutRepository.delete(workout);
    }

    public void update(final Long workoutId, final Workout updateWorkout, final String userId) {
        User user = getUser(userId);
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new WorkoutNotFoundException(ErrorCode.WORKOUT_NOT_FOUND));
        workout.update(updateWorkout, user);
        workoutRepository.save(workout);
    }

    private User getUser(String userId) {
        User user = userRepository.findByUserId(userId);
        if(user == null) throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        return user;
    }

}
