package com.owoonan.owoonan.domain.post.error;

public class BadRequestException extends RuntimeException{

  private final String message;

  public BadRequestException(String message) {
    super(message);
    this.message = message;
  }
}
