package com.owoonan.owoonan.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchDto {
    
    private int offset;
    private int limit;

}
