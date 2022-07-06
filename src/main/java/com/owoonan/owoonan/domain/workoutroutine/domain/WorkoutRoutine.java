package com.owoonan.owoonan.domain.workoutroutine.domain;

import com.owoonan.owoonan.domain.routine.domain.Routine;
import com.owoonan.owoonan.domain.workout.domain.Workout;
import com.owoonan.owoonan.global.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class WorkoutRoutine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id")
    private Routine routine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id")
    private Workout workout;

    @Builder
    public WorkoutRoutine(Long id, Routine routine, Workout workout) {
        this.id = id;
        this.routine = routine;
        this.workout = workout;
    }
}
