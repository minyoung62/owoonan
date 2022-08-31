package com.owoonan.owoonan.domain.post.error;

public class FileWriteException extends RuntimeException{

  private final String message;

  public FileWriteException(String message) {
    super(message);
    this.message = message;
  }
}
