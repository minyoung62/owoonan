package com.owoonan.owoonan.domain.record.repository;

import com.owoonan.owoonan.domain.record.dto.DayRecord;
import com.owoonan.owoonan.domain.record.dto.ResultWorkoutRecordDto;
import com.owoonan.owoonan.domain.record.dto.WorkoutRecordDto;
import com.owoonan.owoonan.domain.record.dto.chart.LineChartSearch;
import com.owoonan.owoonan.domain.record.dto.chart.ResultLineChart;
import com.owoonan.owoonan.domain.record.dto.chart.ResultPieChart;

import java.time.LocalDate;
import java.util.List;

public interface RecordRepositoryCustom {
    List<ResultWorkoutRecordDto>  findRecordForTheMonth(LocalDate localDate, String userId);

    List<DayRecord> findRecordsByTheMonth(LocalDate localDate, String userId);

    ResultPieChart getPieChart(String userId);

    ResultLineChart getLineChart(LineChartSearch lineChartSearch, String userId);
}
