package com.owoonan.owoonan.domain.user.domain.vo;

import com.owoonan.owoonan.domain.user.domain.User;
import com.owoonan.owoonan.domain.workout.domain.Workout;
import com.owoonan.owoonan.domain.workout.domain.vo.WorkoutPart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultWorkout {

    public static List<Workout> init(final User user) {
        List<Workout> workouts = new ArrayList<>();

        // 가슴
        List<String> chestWorkoutNames = Arrays.asList("벤치프레스", "덤벨 프레스", "딥스");
        for(String workoutName: chestWorkoutNames) {
            workouts.add(Workout.builder().workoutName(workoutName).workoutPart(WorkoutPart.CHEST).user(user).build());
        }

        // 등
        List<String> backWorkoutNames = Arrays.asList("데드리프트", "바벨로우", "턱걸이");
        for(String workoutName: backWorkoutNames) {
            workouts.add(Workout.builder().workoutName(workoutName).workoutPart(WorkoutPart.BACK).user(user).build());
        }
        // 어깨
        List<String> shoulderWorkoutNames = Arrays.asList("밀리터리 프레스", "사이드 레터럴 레이즈", "덤벨 숄더 프레스");
        for(String workoutName: shoulderWorkoutNames) {
            workouts.add(Workout.builder().workoutName(workoutName).workoutPart(WorkoutPart.SHOULDER).user(user).build());
        }
        // 팔
        List<String> armWorkoutNames = Arrays.asList("바벨컬", "덤벨컬", "해머컬");
        for(String workoutName: armWorkoutNames) {
            workouts.add(Workout.builder().workoutName(workoutName).workoutPart(WorkoutPart.ARM).user(user).build());
        }
        // 복근
        List<String> absWorkoutNames = Arrays.asList("행잉레그레이즈", "크런치", "윗몸 일으키기");
        for(String workoutName: absWorkoutNames) {
            workouts.add(Workout.builder().workoutName(workoutName).workoutPart(WorkoutPart.ABS).user(user).build());
        }
        // 하체
        List<String> legWorkoutNames = Arrays.asList("스쿼트", "레그컬", "레그프레스");
        for(String workoutName: legWorkoutNames) {
            workouts.add(Workout.builder().workoutName(workoutName).workoutPart(WorkoutPart.LEG).user(user).build());
        }


        return workouts;
    }
}
