package com.owoonan.owoonan.domain.record.repository;

import com.owoonan.owoonan.domain.record.dto.DayRecord;
import com.owoonan.owoonan.domain.record.dto.ResultWorkoutRecordDto;
import com.owoonan.owoonan.domain.record.dto.WorkoutRecordDto;

import java.time.LocalDate;
import java.util.List;

public interface RecordRepositoryCustom {
    List<ResultWorkoutRecordDto>  findRecordForTheMonth(LocalDate localDate, String userId);

    List<DayRecord> findRecordsByTheMonth(LocalDate localDate, String userId);
}
