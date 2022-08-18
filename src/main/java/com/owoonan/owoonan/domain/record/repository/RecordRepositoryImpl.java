package com.owoonan.owoonan.domain.record.repository;

import com.owoonan.owoonan.domain.record.dto.*;
import com.owoonan.owoonan.domain.record.dto.chart.*;
import com.owoonan.owoonan.domain.workout.domain.QWorkout;
import com.owoonan.owoonan.domain.workout.domain.vo.WorkoutPart;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

import static com.owoonan.owoonan.domain.record.domain.QRecord.record;
import static com.owoonan.owoonan.domain.workout.domain.QWorkout.workout;
import static com.owoonan.owoonan.domain.workout.domain.vo.WorkoutPart.*;

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

        List<WorkoutRecordDto> workoutRecordDtos = query.select(Projections.constructor(WorkoutRecordDto.class,
                        record.saveTime, workout.workoutId, workout.workoutName, workout.workoutPart))
                .from(workout).distinct()
                .join(workout.records, record)
                .where(record.userId.eq(userId), record.saveTime.in(resultLocalDates))
                .fetch();

        List<Long> workoutIds = workoutRecordDtos.stream()
                .map(w -> w.getWorkoutId())
                .collect(Collectors.toList());


        List<RecordResponseDto> recordResponseDtos = query.select(Projections.constructor(RecordResponseDto.class,
                        record.saveTime, record.workout.workoutId, record.recordId, record.weight, record.rep))
                .from(record)
                .where(record.saveTime.in(resultLocalDates), record.workout.workoutId.in(workoutIds))
                .fetch();

        Map<LocalDate, Map<Long, List<RecordResponseDto>>> collect = recordResponseDtos.stream()
                .collect(Collectors.groupingBy(RecordResponseDto::getLocalDate,
                        Collectors.groupingBy(RecordResponseDto::getWorkoutId)));

        workoutRecordDtos.forEach(w -> w.setRecordResponseDtos(collect.get(w.getLocalDate()).get(w.getWorkoutId())));

        List<ResultWorkoutRecordDto> resultWorkoutRecordDtos = query.select(
                Projections.constructor(ResultWorkoutRecordDto.class, record.saveTime, workout.workoutPart))
                .from(record).distinct()
                .join(record.workout, workout)
                .where(record.userId.eq(userId), record.saveTime.between(start, end))
                .fetch();

        Map<LocalDate, Map<WorkoutPart, List<WorkoutRecordDto>>> workoutRecordDtoMap = workoutRecordDtos.stream()
                .collect(Collectors.groupingBy(WorkoutRecordDto::getLocalDate,
                        Collectors.groupingBy(WorkoutRecordDto::getWorkoutPart)));

        resultWorkoutRecordDtos.
                forEach(r -> r.setWorkoutRecordDtos(workoutRecordDtoMap.get(r.getLocalDate()).get(r.getWorkoutPart())));


        Map<LocalDate, List<ResultWorkoutRecordDto>> resultWorkoutRecordDtoMap = resultWorkoutRecordDtos.stream()
                .collect(Collectors.groupingBy(ResultWorkoutRecordDto::getLocalDate));

        result.forEach(r -> r.setResultWorkoutRecordDtos(resultWorkoutRecordDtoMap.get(r.getLocalDate())));

        return result;
    }

    @Override
    public ResultPieChart getPieChart(String userId) {
        ArrayList<WorkoutPart> workoutParts = new ArrayList<>(Arrays.asList(CHEST, SHOULDER, BACK, ARM, LEG, ABS));

        List<PieChartOfWorkout> pieCharOfWorkouts = query.select(Projections.constructor(PieChartOfWorkout.class,
                        workout.workoutName,
                        workout.workoutPart,
                        record.weight.multiply(record.rep).sum()))
                .from(workout)
                .join(workout.records, record)
                .where(workout.user.userId.eq(userId))
                .groupBy(workout)
                .fetch();

        Map<WorkoutPart, List<PieChartOfWorkout>> workoutPartsMap = pieCharOfWorkouts.stream()
                .collect(Collectors.groupingBy(PieChartOfWorkout::getWorkoutPart));

        HashMap<WorkoutPart, Double> hashMap = new HashMap<>();
        workoutPartsMap.keySet().forEach(w -> {
            Double sum = workoutPartsMap.get(w).stream().mapToDouble(i -> i.getWeightOfWorkout()).sum();
            hashMap.put(w, sum);
        });

        double total = hashMap.keySet().stream()
                .mapToDouble(k -> hashMap.get(k)).sum();

        pieCharOfWorkouts.forEach(p -> {
            p.setRatioOfWorkout((p.getWeightOfWorkout()/hashMap.get(p.getWorkoutPart())*100.0));
        });

        List<PieChartOfWorkoutPart> pieChartOfWorkoutPart = workoutParts.stream()
                .map(w -> new PieChartOfWorkoutPart(w, 0.0, 0.0))
                .collect(Collectors.toList());


        Map<WorkoutPart, PieChartOfWorkoutPart> pieChartOfWorkoutPartMap = new HashMap<>();

        pieChartOfWorkoutPart.forEach(p -> pieChartOfWorkoutPartMap.put(p.getWorkoutPart(), p));

        workoutPartsMap.keySet().forEach(p -> {
            pieChartOfWorkoutPartMap.get(p)
                    .setPieChartOfWorkouts(workoutPartsMap.get(p));
            pieChartOfWorkoutPartMap.get(p)
                    .setWeightOfWorkoutPart(hashMap.get(p));
            pieChartOfWorkoutPartMap.get(p)
                    .setRatioOfWorkoutPart((double) Math.round((hashMap.get(p)/total)*100.0));
        });


        return new ResultPieChart(pieChartOfWorkoutPart);
    }

    public ResultLineChart getLineChart(LineChartSearch lineChartSearch, String userId) {
        List<Volume> result = query.select(Projections.constructor(Volume.class, record.saveTime, record.weight.multiply(record.rep).sum()))
                .from(record)
                .join(record.workout, workout)
                .where(record.userId.eq(userId),
                        WorkoutPartEq(lineChartSearch.getWorkoutPart()),
                        WorkoutEq(lineChartSearch.getWorkout()))
                .groupBy(record.saveTime)
                .orderBy(record.saveTime.desc())
                .offset(lineChartSearch.getOffset())
                .limit(lineChartSearch.getLimit())
                .fetch();

        return new ResultLineChart(result);
    }

    private BooleanExpression WorkoutEq(String workout) {
        return workout != null ? QWorkout.workout.workoutName.eq(workout) : null;
    }

    private com.querydsl.core.types.dsl.BooleanExpression WorkoutPartEq(WorkoutPart workoutPart) {
        return workoutPart != null ? workout.workoutPart.eq(workoutPart) : null;
    }
}
