package com.owoonan.owoonan.domain.record.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResultLineChart {
    List<Volume>  volumes;
}
