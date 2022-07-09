package com.owoonan.owoonan.domain.workout.dto;

import com.owoonan.owoonan.domain.workout.domain.Workout;
import com.owoonan.owoonan.domain.workout.domain.vo.WorkoutPart;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutCreateRequest {
    @NotNull(message = "운동 잘못된 운동 부위입니다")
    private WorkoutPart workoutPart;
    @NotNull(message = "운동 이름을 입력해주세요")
    private String workoutName;

    public Workout toEntity() {
        return Workout.builder()
                .workoutPart(workoutPart)
                .workoutName(workoutName)
                .build();
    }
}
