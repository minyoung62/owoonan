package com.owoonan.owoonan.domain.record.service;

import com.owoonan.owoonan.domain.record.dto.chart.LineChartSearch;
import com.owoonan.owoonan.domain.record.dto.chart.ResultLineChart;
import com.owoonan.owoonan.domain.record.dto.chart.ResultPieChart;
import com.owoonan.owoonan.domain.record.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StatisticsOfRecordService {

    private final RecordRepository recordRepository;

    @Transactional(readOnly = true)
    public ResultPieChart getPieChart(final String userId) {
        return recordRepository.getPieChart(userId);
    }

    @Transactional(readOnly = true)
    public ResultLineChart getLineChart(final LineChartSearch lineChartSearch, final String userId) {
        return recordRepository.getLineChart(lineChartSearch, userId);
    }
}
