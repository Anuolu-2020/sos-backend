package com.example.sosbackend.util;

import com.example.sosbackend.response.ApiResponse;
import com.example.sosbackend.response.PaginationMetadata;

public class ResponseUtil {

  public static <T> ApiResponse<T> response(T data, Integer page, Integer limit, String message, String path) {
    ApiResponse<T> response = new ApiResponse<>();
    PaginationMetadata metadata = new PaginationMetadata();

    metadata.setPage(page);
    metadata.setLimit(limit);

    response.setSuccess(true);
    response.setMessage(message);
    response.setData(data);
    response.setMetadata(metadata);
    return response;
  }

}
