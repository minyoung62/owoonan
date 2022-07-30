package com.owoonan.owoonan.domain.record.dto;

import com.owoonan.owoonan.domain.record.domain.Record;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecordRequestDto {

    @Range(min = 1, max=2000, message = "세트는 1이상 2000이하여야 합니다")
    private Integer set;


    @Range(min = 1, max=2000, message = "무게는 1kg 이상 2000kg 이하여야 합니다")
    private Double weight;

    @Range(min = 1, max=10000, message = "횟수는 1이상 10000이하여야 합니다")
    private Integer rep;


    public Record toEntity() {
        return Record.builder()
                .set(set)
                .weight(weight)
                .rep(rep)
                .build();
    }
}
