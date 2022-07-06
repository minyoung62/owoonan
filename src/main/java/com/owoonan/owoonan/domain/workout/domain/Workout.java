package com.owoonan.owoonan.domain.workout.domain;

import com.owoonan.owoonan.domain.user.domain.User;
import com.owoonan.owoonan.domain.workout.domain.vo.WorkoutRegion;
import com.owoonan.owoonan.domain.workoutroutine.domain.WorkoutRoutine;
import com.owoonan.owoonan.global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @Column(length = 40, nullable = false)
    private String workoutName;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkoutRegion workoutRegion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL)
    private List<WorkoutRoutine> workoutRoutines = new ArrayList<>();

    @Builder
    public Workout(Long workoutId, String workoutName, WorkoutRegion workoutRegion, User user) {
        this.workoutId = workoutId;
        this.workoutName = workoutName;
        this.workoutRegion = workoutRegion;
        this.user = user;
    }
}
