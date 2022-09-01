package com.owoonan.owoonan.domain.post.dto;

import com.owoonan.owoonan.domain.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateDto {
  private String contents;
  private List<Long> updateImageIds = new ArrayList<>();
  private List<MultipartFile> updateImages = new ArrayList<>();
  public Post toEntity() {
    return Post.builder()
      .content(contents)
      .build();
  }
}
