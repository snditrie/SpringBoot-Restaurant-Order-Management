package com.enigma.wmb_sb.controller;

import com.enigma.wmb_sb.constant.ResponseMessage;
import com.enigma.wmb_sb.model.dto.response.CommonResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<CommonResponse<?>> responseStatusException(ResponseStatusException exception) {
        CommonResponse<?> response = CommonResponse.builder()
                                        .statusCode(exception.getStatusCode().value())
                                        .message(exception.getReason())
                                        .build();
        return ResponseEntity
                    .status(exception.getStatusCode())
                    .body(response);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<CommonResponse<?>> constraintViolationExceptionHandler (ConstraintViolationException e) {
        CommonResponse<?> response = CommonResponse.builder()
                                        .statusCode(HttpStatus.BAD_REQUEST.value())
                                        .message(e.getMessage())
                                        .build();
        return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<CommonResponse<?>> dataIntegrityViolationExceptionHandle (DataIntegrityViolationException e){
        CommonResponse.CommonResponseBuilder<Object> builder = CommonResponse.builder();
        HttpStatus httpStatus;

        if(e.getMessage().contains("foreign key constraint")) {
            builder.statusCode(HttpStatus.BAD_REQUEST.value());
            builder.message("cannot delete data because other table depend on it");
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if(e.getMessage().contains("unique constraint") || e.getMessage().contains("Duplicate entry")) {
            builder.statusCode(HttpStatus.CONFLICT.value());
            builder.message(ResponseMessage.ERROR_ALREADY_EXIST);
            httpStatus = HttpStatus.CONFLICT;
        } else {
            builder.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            builder.message(ResponseMessage.ERROR_INTERNAL_SERVER);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity
                .status(httpStatus)
                .body(builder.build());
    }

}
