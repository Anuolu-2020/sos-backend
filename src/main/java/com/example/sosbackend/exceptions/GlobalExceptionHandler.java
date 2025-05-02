
package com.example.sosbackend.exceptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({ MethodArgumentNotValidException.class, ConstraintViolationException.class,
      UnrecognizedPropertyException.class, DataIntegrityViolationException.class, ResourceNotFoundException.class,
      MissingServletRequestParameterException.class,
      MissingServletRequestPartException.class
  })

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Map<String, Object>> handleValidationExceptions(Exception ex, HttpServletRequest request) {
    Map<String, Object> body = new HashMap<>();
    List<String> errors = new ArrayList<>();

    if (ex instanceof MethodArgumentNotValidException manException) {
      errors = manException.getBindingResult()
          .getFieldErrors()
          .stream()
          .map(DefaultMessageSourceResolvable::getDefaultMessage)
          .collect(Collectors.toList());
    } else if (ex instanceof ConstraintViolationException cvException) {
      errors = cvException.getConstraintViolations()
          .stream()
          .map(violation -> violation.getMessage())
          .collect(Collectors.toList());
    } else if (ex instanceof HttpMessageNotReadableException hmnrEx) {
      Throwable mostSpecificCause = hmnrEx.getMostSpecificCause();
      if (mostSpecificCause instanceof UnrecognizedPropertyException unEx) {
        errors.add("Unrecognized field: " + unEx.getPropertyName());
      } else {
        errors.add(mostSpecificCause.getMessage());
      }
    } else if (ex instanceof DataIntegrityViolationException divEx) {
      errors.add(divEx.getMessage());
    } else if (ex instanceof ResourceNotFoundException notfoundEx) {
      errors.add(notfoundEx.getMessage());
    } else if (ex instanceof MissingServletRequestParameterException missingRequestParamEx) {
      errors.add(missingRequestParamEx.getMessage());
    } else if (ex instanceof MissingServletRequestPartException missingRequestPartEx) {
      errors.add(missingRequestPartEx.getMessage());
    }

    body.put("success", false);
    body.put("path", request.getRequestURI());
    body.put("message", errors);

    return ResponseEntity.badRequest().body(body);
  }

}
