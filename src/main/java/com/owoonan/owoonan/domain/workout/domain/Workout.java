package com.owoonan.owoonan.domain.workout.domain;

import com.owoonan.owoonan.domain.record.domain.Record;
import com.owoonan.owoonan.domain.user.domain.User;
import com.owoonan.owoonan.domain.workout.domain.vo.WorkoutPart;
import com.owoonan.owoonan.domain.workout.error.WorkoutMissMatchException;
import com.owoonan.owoonan.domain.workout.error.WorkoutNameDuplicationException;
import com.owoonan.owoonan.domain.workoutroutine.domain.WorkoutRoutine;
import com.owoonan.owoonan.global.common.BaseEntity;
import com.owoonan.owoonan.global.error.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Workout extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long workoutId;

    @Size(min=1, max=30, message = "1자~30자의 루틴 이름여야 합니다")
    @NotNull
    private String workoutName;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkoutPart workoutPart;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL)
    private List<WorkoutRoutine> workoutRoutines = new ArrayList<>();

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL)
    private List<Record> records = new ArrayList<>();

    @Builder
    public Workout(Long workoutId, String workoutName, WorkoutPart workoutPart, User user) {
        this.workoutId = workoutId;
        this.workoutName = workoutName;
        this.workoutPart = workoutPart;
        this.user = user;
    }

    public Workout addUser(final User user) {
        this.user = user;
        return this;
    }

    public void update(final Workout updateWorkout, final User user) {
        if (!this.getUser().getUserId().equals(user.getUserId())) throw new WorkoutMissMatchException(ErrorCode.WORKOUT_MISS_MATCH);
        if (this.workoutName.equals(updateWorkout.getWorkoutName())) throw new WorkoutNameDuplicationException(ErrorCode.WORKOUT_NAME_DUPLICATION);
        this.workoutName = updateWorkout.getWorkoutName();
    }
}