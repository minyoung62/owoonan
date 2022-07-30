package com.owoonan.owoonan.domain.record.domain;

import com.owoonan.owoonan.domain.record.error.RecordMissMatchException;
import com.owoonan.owoonan.domain.workout.domain.Workout;
import com.owoonan.owoonan.global.common.BaseEntity;
import com.owoonan.owoonan.global.error.exception.ErrorCode;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @Column(updatable = false)
    private LocalDate saveTime;

    private String userId;

    @Range(min = 1, max=2000, message = "횟수는 1이상 2000이하여야 합니다")
    private Integer set;


    @Range(min = 1, max=2000, message = "무게는 1kg 이상 2000kg 이하여야 합니다")
    private Double weight;

    @Range(min = 1, max=10000, message = "횟수는 1이상 10000이하여야 합니다")
    private Integer rep;

    private Integer breakTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id")
    private Workout workout;


    public void addWorkout(Workout workout) {
        this.workout = workout;
    }

    public void addUserId(String userId) {
        this.userId = userId;
    }

    public void patch(Record updateRecord, String userId) {
        if(!this.userId.equals(userId)) throw new RecordMissMatchException(ErrorCode.RECORD_MISS_MATCH);
        this.rep = updateRecord.getRep();
        this.weight = updateRecord.getWeight();
    }

    public void addSaveTime(LocalDate savTime) {
        this.saveTime = savTime;
    }
}
