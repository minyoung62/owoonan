package com.owoonan.owoonan.domain.workout.repository;

import com.owoonan.owoonan.domain.workout.domain.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, Long>, WorkoutRepositoryCustom {
}
