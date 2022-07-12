package com.owoonan.owoonan.domain.routine.repository;

import com.owoonan.owoonan.domain.routine.domain.Routine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutineRepository extends JpaRepository<Routine, Long>, RoutineRepositoryCustom {
}
