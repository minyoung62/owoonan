package com.owoonan.owoonan.domain.record.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecordCreateRequest {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate saveDay;

    private List<RecordsCreateRequestDto> requestDtos;
}
