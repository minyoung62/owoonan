package com.owoonan.owoonan.domain.comment.dto;

import com.owoonan.owoonan.domain.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateRequestDto {

  private String contents;

  public Comment toEntity() {
    return Comment
      .builder()
      .contents(contents)
      .build();

  }
}
