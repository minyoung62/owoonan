package com.owoonan.owoonan.domain.workout.domain;

import com.owoonan.owoonan.domain.record.domain.Record;
import com.owoonan.owoonan.domain.routine.domain.Routine;
import com.owoonan.owoonan.domain.user.domain.User;
import com.owoonan.owoonan.domain.workout.domain.vo.WorkoutRegion;
import com.owoonan.owoonan.global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
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

    private Integer goalSet;

    private Double goalWeight;

    private Integer goalRep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id")
    private Routine routine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL)
    private List<Record> records = new ArrayList<>();

    @Builder
    public Workout(Long workoutId, String workoutName, WorkoutRegion workoutRegion, Integer goalSet, Double goalWeight, Integer goalRep, Routine routine, User user) {
        this.workoutId = workoutId;
        this.workoutName = workoutName;
        this.workoutRegion = workoutRegion;
        this.goalSet = goalSet;
        this.goalWeight = goalWeight;
        this.goalRep = goalRep;
        this.routine = routine;
        this.user = user;
    }
}
