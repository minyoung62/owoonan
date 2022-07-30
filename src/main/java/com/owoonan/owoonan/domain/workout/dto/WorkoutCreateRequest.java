package com.owoonan.owoonan.domain.workout.dto;

import com.owoonan.owoonan.domain.workout.domain.Workout;
import com.owoonan.owoonan.domain.workout.domain.vo.WorkoutPart;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutCreateRequest {
    @NotNull(message = "운동 부위는 필수로 선택해야합니다")
    private WorkoutPart workoutPart;
    @NotNull(message = "운동 이름을 입력해주세요")
    @Size(min=1, max=30, message = "1자~30자의 운동 이름여야 합니다")
    private String workoutName;

    public Workout toEntity() {
        return Workout.builder()
                .workoutPart(workoutPart)
                .workoutName(workoutName)
                .build();
    }
}
