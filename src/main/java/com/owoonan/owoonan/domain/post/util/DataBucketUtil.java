package com.owoonan.owoonan.domain.post.util;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.owoonan.owoonan.domain.post.dto.FileDto;
import com.owoonan.owoonan.domain.post.error.BadRequestException;
import com.owoonan.owoonan.domain.post.error.FileWriteException;
import com.owoonan.owoonan.domain.post.error.GCPFileUploadException;
import com.owoonan.owoonan.domain.post.error.InvalidFileTypeException;
import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DataBucketUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(DataBucketUtil.class);

  @Value("${gcp.config.file}")
  private String gcpConfigFile;

  @Value("${gcp.project.id}")
  private String gcpProjectId;

  @Value("${gcp.bucket.id}")
  private String gcpBucketId;

  @Value("${gcp.dir.name}")
  private String gcpDirectoryName;

  public void deleteFile(String objectName) throws IOException {
    InputStream inputStream = new ClassPathResource(gcpConfigFile).getInputStream();

    StorageOptions options = StorageOptions.newBuilder().setProjectId(gcpProjectId)
      .setCredentials(GoogleCredentials.fromStream(inputStream)).build();

    Storage storage = options.getService();
    storage.delete(gcpBucketId, objectName);
  }

  public FileDto uploadFile(MultipartFile multipartFile, String fileName, String contentType) {

    try{

      LOGGER.debug("Start file uploading process on GCS");
      byte[] fileData = multipartFile.getBytes();

      Bucket bucket = getBucket();

      RandomString id = new RandomString(6, ThreadLocalRandom.current());
      Blob blob = bucket.create(gcpDirectoryName + "/" + fileName + "-" + id.nextString() + checkFileExtension(fileName), fileData, contentType);

      if(blob != null){
        LOGGER.debug("File successfully uploaded to GCS");
        return new FileDto(blob.getName(), blob.getMediaLink());
      }

    }catch (Exception e){
      LOGGER.error("An error occurred while uploading data. Exception: ", e);
      throw new GCPFileUploadException("An error occurred while storing data to GCS");
    }
    throw new GCPFileUploadException("An error occurred while storing data to GCS");
  }

  private Bucket getBucket() throws IOException {
    InputStream inputStream = new ClassPathResource(gcpConfigFile).getInputStream();

    StorageOptions options = StorageOptions.newBuilder().setProjectId(gcpProjectId)
      .setCredentials(GoogleCredentials.fromStream(inputStream)).build();

    Storage storage = options.getService();


    Bucket bucket = storage.get(gcpBucketId,Storage.BucketGetOption.fields(Storage.BucketField.IAMCONFIGURATION));
    return bucket;
  }
  private File convertFile(MultipartFile file) {

    try{
      if(file.getOriginalFilename() == null){
        throw new BadRequestException("Original file name is null");
      }
      File convertedFile = new File(file.getOriginalFilename());
  //    FileOutputStream outputStream = new FileOutputStream(convertedFile);
//      outputStream.write(file.getBytes());
//      outputStream.close();
      LOGGER.debug("Converting multipart file : {}", convertedFile);
      return convertedFile;
    }catch (Exception e){
      throw new FileWriteException("An error has occurred while converting the file");
    }
  }

  private String checkFileExtension(String fileName) {
    if(fileName != null && fileName.contains(".")){
      String[] extensionList = {".png", ".jpeg", ".pdf", ".doc", ".mp3"};

      for(String extension: extensionList) {
        if (fileName.endsWith(extension)) {
          LOGGER.debug("Accepted file type : {}", extension);
          return extension;
        }
      }
    }
    LOGGER.error("Not a permitted file type");
    throw new InvalidFileTypeException("Not a permitted file type");
  }
}
