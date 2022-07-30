package com.owoonan.owoonan.domain.record.dto;

import com.owoonan.owoonan.domain.record.domain.Record;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecordUpdateRequestDto {

    @Range(min = 1, max=2000, message = "무게는 1kg 이상 2000kg 이하여야 합니다")
    private Double weight;

    @Range(min = 1, max=10000, message = "횟수는 1이상 10000이하여야 합니다")
    private Integer rep;


    public Record toEntity() {
        return Record.builder()
                .weight(weight)
                .rep(rep)
                .build();
    }
}
