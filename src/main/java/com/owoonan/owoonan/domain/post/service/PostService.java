package com.owoonan.owoonan.domain.post.service;

import com.owoonan.owoonan.domain.image.domain.Image;
import com.owoonan.owoonan.domain.post.domain.Post;
import com.owoonan.owoonan.domain.post.error.PostMissMatchException;
import com.owoonan.owoonan.domain.post.error.PostNotFoundException;
import com.owoonan.owoonan.domain.post.repository.PostRepository;
import com.owoonan.owoonan.domain.post.util.DataBucketUtil;
import com.owoonan.owoonan.domain.post.dto.FileDto;
import com.owoonan.owoonan.domain.post.error.GCPFileUploadException;
import com.owoonan.owoonan.domain.user.domain.User;
import com.owoonan.owoonan.domain.user.error.UserNotFoundException;
import com.owoonan.owoonan.global.error.exception.ErrorCode;
import com.owoonan.owoonan.global.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final DataBucketUtil dataBucketUtil;

    public Long create(final Post requestPost, List<MultipartFile> files , final String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);

        List<Image> images = new ArrayList<>();
        if (files.size() != 0) {
          files.forEach(file -> {
            String fileName = file.getOriginalFilename();
            Path path = new File(fileName).toPath();
            try {
              String contentType = Files.probeContentType(path);
              FileDto fileDto = dataBucketUtil.uploadFile(file, fileName, contentType);
              if (fileDto != null) {
                Image image = Image.builder()
                  .imageName(fileDto.getFileName())
                  .imageUrl(fileDto.getFileUrl())
                  .build();

                images.add(image);
              }
            } catch (IOException e) {

              throw new GCPFileUploadException("Error occurred while uploading");
            }
          });
        }

      requestPost.addUser(user);
      requestPost.addImages(images);
      Post savePost = postRepository.save(requestPost);

      return savePost.getId();
    }

    public void update(final Long postId, final Post updatedPost, final String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
        if (!userId.equals(post.getUser().getUserId())) throw new PostMissMatchException(ErrorCode.POST_NOT_MISSMATCH);
        post.patch(updatedPost);

        postRepository.save(post);
    }

    public void delete(Long postId, String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
        if (!userId.equals(post.getUser().getUserId())) throw new PostMissMatchException(ErrorCode.POST_NOT_MISSMATCH);

        postRepository.delete(post);
    }

}
