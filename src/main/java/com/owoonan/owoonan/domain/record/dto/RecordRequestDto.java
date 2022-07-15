package com.owoonan.owoonan.domain.record.dto;

import com.owoonan.owoonan.domain.record.domain.Record;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecordRequestDto {

    @NotNull
    private Integer set;

    @NotNull
    private Double weight;

    @NotNull
    private Integer rep;

    public Record toEntity() {
        return Record.builder()
                .set(set)
                .weight(weight)
                .rep(rep)
                .build();
    }
}
