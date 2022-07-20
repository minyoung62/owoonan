package com.owoonan.owoonan.domain.record.service;

import com.owoonan.owoonan.domain.record.dto.chart.LineChartSearch;
import com.owoonan.owoonan.domain.record.dto.chart.ResultLineChart;
import com.owoonan.owoonan.domain.record.dto.chart.ResultPieChart;
import com.owoonan.owoonan.domain.record.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsOfRecordService {

    private final RecordRepository recordRepository;

    public ResultPieChart getPieChart(final String userId) {
        return recordRepository.getPieChart(userId);
    }

    public ResultLineChart getLineChart(final LineChartSearch lineChartSearch, final String userId) {
        return recordRepository.getLineChart(lineChartSearch, userId);
    }
}
