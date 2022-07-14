package com.owoonan.owoonan.domain.routine.domain;

import com.owoonan.owoonan.domain.routine.domain.vo.Part;
import com.owoonan.owoonan.domain.user.domain.User;
import com.owoonan.owoonan.domain.workout.domain.Workout;
import com.owoonan.owoonan.domain.workoutroutine.domain.WorkoutRoutine;
import com.owoonan.owoonan.global.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "routine")
public class Routine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long routineId;

    @Column(length = 40,nullable = false)
    private String routineName;

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL)
    private List<Part> parts = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "routine",  cascade = CascadeType.ALL)
    private List<WorkoutRoutine> workoutRoutines = new ArrayList<>();

    @Builder
    public Routine(Long routineId, String routineName, User user) {
        this.routineId = routineId;
        this.routineName = routineName;
        this.user = user;
    }

    public void addUser(User user) {
        this.user = user;
    }

    public void addWorkoutRoutines(List<WorkoutRoutine> workoutRoutines) {
        for(WorkoutRoutine workoutRoutine: workoutRoutines) {
            this.workoutRoutines.add(workoutRoutine);
            workoutRoutine.addRoutine(this);
        }
    }

    public void addParts(List<Part> parts) {
        for(Part part: parts) {
            this.parts.add(part);
            part.addRoutine(this);
        }
    }

    public void patch(Routine updateRoutine, List<WorkoutRoutine> workoutRoutines, List<Part> parts) {
        this.routineName = updateRoutine.getRoutineName();
        addWorkoutRoutines(workoutRoutines);
        addParts(parts);
    }
}
