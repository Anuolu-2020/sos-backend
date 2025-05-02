package com.example.sosbackend.response;

public class ApiResponse<T> {
  private boolean success;
  private String message;
  private T data;
  private PaginationMetadata metadata;

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public PaginationMetadata getMetadata() {
    return metadata;
  }

  public void setMetadata(PaginationMetadata metadata) {
    this.metadata = metadata;
  }

}
