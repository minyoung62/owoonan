package com.owoonan.owoonan.domain.routine.dto;

import com.owoonan.owoonan.domain.routine.domain.Routine;
import com.owoonan.owoonan.domain.workout.domain.vo.WorkoutPart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RoutineCreateRequestDto {

    @Size(min=1, max=30, message = "1자~30자의 루틴 이름여야 합니다")
    @NotNull
    private String routineName;
    private List<Long> workoutIds;
    @NotNull
    private List<WorkoutPart> workoutParts;
    public Routine toEntity() {
        return Routine.builder()
                .routineName(routineName)
                .build();
    }
}
