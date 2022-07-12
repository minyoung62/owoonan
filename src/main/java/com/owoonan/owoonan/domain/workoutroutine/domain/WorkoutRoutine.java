package com.owoonan.owoonan.domain.workoutroutine.domain;

import com.owoonan.owoonan.domain.routine.domain.Routine;
import com.owoonan.owoonan.domain.workout.domain.Workout;
import com.owoonan.owoonan.domain.workout.error.WorkoutMissMatchException;
import com.owoonan.owoonan.domain.workout.error.WorkoutNotFoundException;
import com.owoonan.owoonan.global.common.BaseEntity;
import com.owoonan.owoonan.global.error.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class WorkoutRoutine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

    public static List<WorkoutRoutine> createWorkoutRoutines (List<Workout> workouts, String userId) {
        List<WorkoutRoutine> workoutRoutines = new ArrayList<>();

        for(Workout w: workouts) {
            System.out.println(w.getWorkoutId());
            if(w == null) throw new WorkoutNotFoundException(ErrorCode.WORKOUT_NOT_FOUND);
            if(w.getUser().getUserId() != userId) throw new WorkoutMissMatchException(ErrorCode.WORKOUT_MISS_MATCH);
            WorkoutRoutine workoutRoutine = new WorkoutRoutine();
            workoutRoutine.setWorkout(w);
            workoutRoutines.add(workoutRoutine);
        }
        return workoutRoutines;
    }


    private void setWorkout(Workout workout) {
        this.workout = workout;
    }


    public void addRoutine(Routine routine) {
        this.routine = routine;
    }
}
