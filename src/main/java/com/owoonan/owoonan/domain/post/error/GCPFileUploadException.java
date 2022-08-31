package com.owoonan.owoonan.domain.post.error;

public class GCPFileUploadException extends RuntimeException{

  private final String message;

  public GCPFileUploadException(String message) {
    super(message);
    this.message = message;
  }
}
