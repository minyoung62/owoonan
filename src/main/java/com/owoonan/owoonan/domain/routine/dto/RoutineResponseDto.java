package com.owoonan.owoonan.domain.routine.dto;

import com.owoonan.owoonan.domain.workout.domain.vo.WorkoutPart;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoutineResponseDto {

    private Long routineId;
    private String routineName;
    private List<PartDto> partDtos;

    @QueryProjection
    public RoutineResponseDto(Long routineId, String routineName) {
        this.routineId = routineId;
        this.routineName = routineName;
    }
}
