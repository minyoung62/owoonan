package com.owoonan.owoonan.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.owoonan.owoonan.domain.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PostCreateRequest {

  @NotBlank(message = "본문을 입력해주세요")
  private String contents;


  private List<MultipartFile> images = new ArrayList<>();

  public Post toEntity() {
    return Post.builder()
      .content(contents)
      .build();

  }
}
