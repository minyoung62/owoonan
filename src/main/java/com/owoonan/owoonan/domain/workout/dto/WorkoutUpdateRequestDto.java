package com.owoonan.owoonan.domain.workout.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import com.owoonan.owoonan.domain.workout.domain.Workout;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WorkoutUpdateRequestDto {
    @NotNull(message = "운동 변경할 이름을 입력해주세요")
    private String workoutName;

    public Workout toEntity() {
        return Workout.builder()
                .workoutName(workoutName)
                .build();
    }
}
