package com.owoonan.owoonan.domain.record.dto;

import com.owoonan.owoonan.domain.record.domain.Record;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecordUpdateRequestDto {

    private Double weight;

    private Integer rep;

    public Record toEntity() {
        return Record.builder()
                .weight(weight)
                .rep(rep)
                .build();
    }
}
