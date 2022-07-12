package com.owoonan.owoonan.domain.routine.domain.vo;

import com.owoonan.owoonan.domain.routine.domain.Routine;
import com.owoonan.owoonan.domain.workout.domain.vo.WorkoutPart;
import com.owoonan.owoonan.global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Part extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "routine_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Routine routine;

    @Enumerated(EnumType.STRING)
    private WorkoutPart workoutPart;

    public static List<Part> createParts(List<WorkoutPart> workoutParts) {
        List<Part> parts= new ArrayList<>();
        for(WorkoutPart wp : workoutParts) {
            Part part = Part.builder().workoutPart(wp).build();
            parts.add(part);
        }
        return parts;
    }

    public void addRoutine(Routine routine) {
        this.routine = routine;
    }
}
