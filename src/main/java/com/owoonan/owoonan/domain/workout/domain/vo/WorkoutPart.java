package com.owoonan.owoonan.domain.workout.domain.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WorkoutPart {
    Chest("가슴"),
    SHOULDER("어깨"),
    BACK("등"),
    ARM("팔"),
    LEG("하체"),
    ABS("복근");

    private final String part;


    public static WorkoutPart of(String workoutPart) {
        if (workoutPart == null ){
            throw new IllegalArgumentException("잘못된 운동 부위를 선택해주세요");
        }

        for(WorkoutPart wp : WorkoutPart.values()) {
            if (wp.part.equals(workoutPart)) {
                return wp;
            }
        }
        throw new IllegalArgumentException("잘못된 운동 부위입니다");
    }

    @JsonValue
    public String getValue() {
        return part;
    }
}
