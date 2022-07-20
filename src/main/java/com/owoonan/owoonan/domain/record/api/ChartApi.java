package com.owoonan.owoonan.domain.record.api;

import com.owoonan.owoonan.domain.record.dto.chart.LineChartSearch;
import com.owoonan.owoonan.domain.record.dto.chart.ResultLineChart;
import com.owoonan.owoonan.domain.record.dto.chart.ResultPieChart;
import com.owoonan.owoonan.domain.record.service.StatisticsOfRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Response;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chart")
@RequiredArgsConstructor
@Api(tags = "통계 기능")
public class ChartApi {
    
    private final StatisticsOfRecordService statisticsOfRecordService;

    @GetMapping("pieChart")
    @ApiOperation(value = "파이(원형) 차트 조회")
    public ResponseEntity<ResultPieChart> getPieChart() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(statisticsOfRecordService.getPieChart(getPrincipal().getUsername()));
    }

    @GetMapping("lineChart")
    @ApiOperation(value = "막대(꺾은선) 그래프 조회")
    public ResponseEntity<ResultLineChart> getLineChart(@ModelAttribute("lineChartSearch")LineChartSearch lineChartSearch) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(statisticsOfRecordService.getLineChart(lineChartSearch, getPrincipal().getUsername()));
    }

    public User getPrincipal() {
        return (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
