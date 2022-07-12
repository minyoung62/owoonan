package com.owoonan.owoonan.domain.record.domain;

import com.owoonan.owoonan.domain.workout.domain.Workout;
import com.owoonan.owoonan.global.common.BaseEntity;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Record extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long recordId;

    @NotNull
    private Integer set;

    @NotNull
    private Double weight;

    @NotNull
    private Integer rep;

    @NotNull
    private Integer breakTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id")
    private Workout workout;


}
