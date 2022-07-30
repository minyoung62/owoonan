package com.owoonan.owoonan.domain.record.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecordCreateRequest {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate saveDate;

    @NotNull
    private List<@Valid RecordsCreateRequestDto> requestDtos;
}
