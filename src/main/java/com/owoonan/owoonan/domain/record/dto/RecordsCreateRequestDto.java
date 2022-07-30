package com.owoonan.owoonan.domain.record.dto;


import com.owoonan.owoonan.domain.record.domain.Record;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecordsCreateRequestDto {

    @NotNull
    private Long workoutId;

    @NotNull
    private List<@Valid RecordRequestDto> recordDtos;

    public List<Record> toEntity() {
        return recordDtos.stream()
                .map(r -> r.toEntity())
                .collect(Collectors.toList());

    }
}
