package com.services.ms.student.app.infrastructure.adapters.input.rest;

import com.services.ms.student.app.domain.exception.StudentNotFoundException;
import com.services.ms.student.app.domain.model.ErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

import static com.services.ms.student.app.utils.ErrorCatalog.GENERIC_ERROR;
import static com.services.ms.student.app.utils.ErrorCatalog.INVALID_STUDENT;
import static com.services.ms.student.app.utils.ErrorCatalog.STUDENT_NOT_FOUND;

@RestControllerAdvice
public class GlobalControllerAdvice {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(StudentNotFoundException.class)
  public ErrorResponse handleStudentNotFoundException() {
    return ErrorResponse.builder()
        .code(STUDENT_NOT_FOUND.getCode())
        .message(STUDENT_NOT_FOUND.getMessage())
        .timestamp(LocalDateTime.now())
        .build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ErrorResponse handleMethodArgumentNotValidException(
      MethodArgumentNotValidException exception) {
    BindingResult result = exception.getBindingResult();

    return ErrorResponse.builder()
        .code(INVALID_STUDENT.getCode())
        .message(INVALID_STUDENT.getMessage())
        .details(result.getFieldErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList()))
        .timestamp(LocalDateTime.now())
        .build();
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public ErrorResponse handleGenericError(Exception exception) {
    return ErrorResponse.builder()
        .code(GENERIC_ERROR.getCode())
        .message(GENERIC_ERROR.getMessage())
        .details(Collections.singletonList(exception.getMessage()))
        .timestamp(LocalDateTime.now())
        .build();
  }

}
