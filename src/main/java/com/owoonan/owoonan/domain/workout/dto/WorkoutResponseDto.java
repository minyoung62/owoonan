package com.owoonan.owoonan.domain.workout.dto;

import com.owoonan.owoonan.domain.workout.domain.Workout;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Data
public class WorkoutResponseDto {

    private Long workoutId;
    private String workoutName;

    public static WorkoutResponseDto of(Workout workout) {
        return WorkoutResponseDto.builder()
                .workoutName(workout.getWorkoutName())
                .workoutId(workout.getWorkoutId())
                .build();
    }

}
