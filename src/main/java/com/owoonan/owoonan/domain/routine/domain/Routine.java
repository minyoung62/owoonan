package com.owoonan.owoonan.domain.routine.domain;

import com.owoonan.owoonan.domain.user.domain.User;
import com.owoonan.owoonan.domain.workout.domain.Workout;
import com.owoonan.owoonan.global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
public class Routine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long routineId;

    @Column(length = 40,nullable = false)
    private String routineName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "routine",cascade = CascadeType.ALL)
    private List<Workout> workouts = new ArrayList<>();

    @Builder
    public Routine(Long routineId, String routineName, User user) {
        this.routineId = routineId;
        this.routineName = routineName;
        this.user = user;
    }
}
