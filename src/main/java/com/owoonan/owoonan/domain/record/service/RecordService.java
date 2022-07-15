package com.owoonan.owoonan.domain.record.service;

import com.owoonan.owoonan.domain.record.domain.Record;
import com.owoonan.owoonan.domain.record.dto.DayRecord;
import com.owoonan.owoonan.domain.record.dto.RecordsCreateRequestDto;
import com.owoonan.owoonan.domain.record.dto.ResultWorkoutRecordDto;
import com.owoonan.owoonan.domain.record.dto.WorkoutRecordDto;
import com.owoonan.owoonan.domain.record.error.RecordMissMatchException;
import com.owoonan.owoonan.domain.record.error.RecordNotFoundException;
import com.owoonan.owoonan.domain.record.repository.RecordRepository;
import com.owoonan.owoonan.domain.user.domain.User;
import com.owoonan.owoonan.domain.user.error.UserNotFoundException;
import com.owoonan.owoonan.domain.workout.domain.Workout;
import com.owoonan.owoonan.domain.workout.error.WorkoutMissMatchException;
import com.owoonan.owoonan.domain.workout.error.WorkoutNotFoundException;
import com.owoonan.owoonan.domain.workout.repository.WorkoutRepository;
import com.owoonan.owoonan.global.error.exception.ErrorCode;
import com.owoonan.owoonan.global.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {

    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;
    private final RecordRepository recordRepository;

    public void create(final List<RecordsCreateRequestDto> requestDtos, final LocalDate savTime, final String userId) {
        User user = getUser(userId);
        for(RecordsCreateRequestDto requestDto: requestDtos) {
            List<Record> records = requestDto.toEntity();
            Long workoutId = requestDto.getWorkoutId();
            Workout workout = workoutRepository.findById(workoutId)
                    .orElseThrow(() -> new WorkoutNotFoundException(ErrorCode.WORKOUT_NOT_FOUND));
            if (!workout.getUser().getUserId().equals(user.getUserId()))
                throw new WorkoutMissMatchException(ErrorCode.WORKOUT_MISS_MATCH);

            for (Record record : records) {
                record.addWorkout(workout);
                record.addSaveTime(savTime);
                record.addUserId(user.getUserId());
            }

            recordRepository.saveAll(records);
        }
    }

    public void update(final Long recordId, final Record updateRecord, final String userId) {
        User user = getUser(userId);
        Record record = recordRepository.findById(recordId)
                .orElseThrow(() -> new RecordNotFoundException(ErrorCode.RECORD_NOT_FOUND));

        record.patch(updateRecord, user.getUserId());
    }

    public void delete(final Long recordId, final String userId) {
        User user = getUser(userId);
        Record record = recordRepository.findById(recordId)
                .orElseThrow(() -> new RecordNotFoundException(ErrorCode.RECORD_NOT_FOUND));
        if(!record.getUserId().equals(userId)) throw new RecordMissMatchException(ErrorCode.RECORD_MISS_MATCH);

        recordRepository.delete(record);
    }

    @Transactional(readOnly = true)
    public List<ResultWorkoutRecordDto> findRecordsForTheMonth(final LocalDate localDate, final String userId) {
        return recordRepository.findRecordForTheMonth(localDate, userId);
    }

    @Transactional(readOnly = true)
    public List<DayRecord> findRecords(final LocalDate localDate, final String userId) {
        return recordRepository.findRecordsByTheMonth(localDate, userId);
    }


    private User getUser(String userId) {
        User user = userRepository.findByUserId(userId);
        if(user == null) throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        return user;
    }
}
