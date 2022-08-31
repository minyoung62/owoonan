package com.owoonan.owoonan.domain.post.util;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public class GivenImage {
  private static String imgPath1 = "test1.png";
  private static String imgName1 = "test1";
  private static String imgPath2 = "test2.png";
  private static String imgName2 = "test2";
  private final static List<MultipartFile> files = List.of(
    new MockMultipartFile(imgName1, imgPath1, MediaType.IMAGE_PNG_VALUE, imgName1.getBytes()),
    new MockMultipartFile(imgName2, imgPath2, MediaType.IMAGE_PNG_VALUE, imgName2.getBytes())
  );

  public static List<MultipartFile> getImages() {
    return GivenImage.files;
  }


}
