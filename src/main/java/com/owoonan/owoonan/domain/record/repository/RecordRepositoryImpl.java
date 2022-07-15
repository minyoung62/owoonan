package com.owoonan.owoonan.domain.record.repository;

import com.owoonan.owoonan.domain.record.dto.*;
import com.owoonan.owoonan.domain.workout.domain.vo.WorkoutPart;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.owoonan.owoonan.domain.record.domain.QRecord.record;
import static com.owoonan.owoonan.domain.workout.domain.QWorkout.workout;

@RequiredArgsConstructor
public class RecordRepositoryImpl implements RecordRepositoryCustom{

    private final JPAQueryFactory query;

    public List<ResultWorkoutRecordDto> findRecordForTheMonth(LocalDate localDate, String userId) {
        LocalDateTime startDay = localDate.atTime(0, 0, 0, 0);
        LocalDateTime endDay = localDate.atTime(23, 59, 59, 999999999);

//        List<WorkoutPart> workoutParts = query.select(workout.workoutPart)
//                .from(workout).distinct()
//                .join(workout.records, record)
//                .where(record.userId.eq(userId), record.createTime.between(startDay, endDay))
//                .fetch();
//        for(WorkoutPart wp: result) {
//            System.out.println(wp);
//        }

        List<WorkoutRecordDto> workoutRecordDtos = query.select(Projections.constructor(WorkoutRecordDto.class, workout.workoutId, workout.workoutName, workout.workoutPart))
                .from(workout).distinct()
                .join(workout.records, record)
                .where(record.userId.eq(userId), record.createTime.between(startDay, endDay))
                .fetch();

        List<Long> workoutIds = workoutRecordDtos.stream()
                .map(r -> r.getWorkoutId())
                .collect(Collectors.toList());

        List<RecordResponseDto> recordResponseDtos = query.select(Projections.constructor(RecordResponseDto.class, record.workout.workoutId, record.recordId, record.weight, record.rep))
                .from(record)
                .where(record.workout.workoutId.in(workoutIds), record.createTime.between(startDay, endDay))
                .fetch();

        Map<Long, List<RecordResponseDto>> recordResponseDtoMap = recordResponseDtos.stream()
                .collect(Collectors.groupingBy(RecordResponseDto::getWorkoutId));

        workoutRecordDtos.forEach(r -> r.setRecordResponseDtos(recordResponseDtoMap.get(r.getWorkoutId())));
        Map<WorkoutPart, List<WorkoutRecordDto>> collect = workoutRecordDtos.stream()
                .collect(Collectors.groupingBy(WorkoutRecordDto::getWorkoutPart));

        List<ResultWorkoutRecordDto> results = new ArrayList<>();

        for(WorkoutPart wp: collect.keySet()){
            results.add(ResultWorkoutRecordDto.builder().workoutPart(wp).build());
        }

        results.forEach(r -> r.setWorkoutRecordDtos(collect.get(r.getWorkoutPart())));

        return results;
    }

    public List<DayRecord> findRecordsByTheMonth(LocalDate localDate, String userId) {
        YearMonth targetYearMonth = YearMonth.from(localDate);
        LocalDate start = targetYearMonth.atDay(1);
        LocalDate end = targetYearMonth.atEndOfMonth();
        
        List<DayRecord> result = query.select(Projections.constructor(DayRecord.class, record.saveTime))
                .from(record).distinct()
                .where(record.userId.eq(userId), record.saveTime.between(start, end))
                .fetch();

        List<LocalDate> resultLocalDates = result.stream().map(r -> r.getLocalDate())
                .collect(Collectors.toList());

        List<WorkoutRecordDto> workoutRecordDtos = query.select(Projections.constructor(WorkoutRecordDto.class, record.saveTime, workout.workoutId, workout.workoutName, workout.workoutPart))
                .from(workout).distinct()
                .join(workout.records, record)
                .where(record.userId.eq(userId), record.saveTime.in(resultLocalDates))
                .fetch();

        List<Long> workoutIds = workoutRecordDtos.stream()
                .map(w -> w.getWorkoutId())
                .collect(Collectors.toList());


        List<RecordResponseDto> recordResponseDtos = query.select(Projections.constructor(RecordResponseDto.class, record.saveTime, record.workout.workoutId, record.recordId, record.weight, record.rep))
                .from(record)
                .where(record.saveTime.in(resultLocalDates), record.workout.workoutId.in(workoutIds))
                .fetch();

        Map<LocalDate, Map<Long, List<RecordResponseDto>>> collect = recordResponseDtos.stream()
                .collect(Collectors.groupingBy(RecordResponseDto::getLocalDate, Collectors.groupingBy(RecordResponseDto::getWorkoutId)));

        workoutRecordDtos.forEach(w -> w.setRecordResponseDtos(collect.get(w.getLocalDate()).get(w.getWorkoutId())));

        List<ResultWorkoutRecordDto> resultWorkoutRecordDtos = query.select(Projections.constructor(ResultWorkoutRecordDto.class, record.saveTime, workout.workoutPart))
                .from(record).distinct()
                .join(record.workout, workout)
                .where(record.userId.eq(userId), record.saveTime.between(start, end))
                .fetch();

        Map<LocalDate, Map<WorkoutPart, List<WorkoutRecordDto>>> workoutRecordDtoMap = workoutRecordDtos.stream()
                .collect(Collectors.groupingBy(WorkoutRecordDto::getLocalDate, Collectors.groupingBy(WorkoutRecordDto::getWorkoutPart)));

        resultWorkoutRecordDtos.forEach(r -> r.setWorkoutRecordDtos(workoutRecordDtoMap.get(r.getLocalDate()).get(r.getWorkoutPart())));


        Map<LocalDate, List<ResultWorkoutRecordDto>> resultWorkoutRecordDtoMap = resultWorkoutRecordDtos.stream()
                .collect(Collectors.groupingBy(ResultWorkoutRecordDto::getLocalDate));

        result.forEach(r -> r.setResultWorkoutRecordDtos(resultWorkoutRecordDtoMap.get(r.getLocalDate())));

        return result;
    }


}
