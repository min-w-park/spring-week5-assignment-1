package com.codesoom.assignment.controllers;

import com.codesoom.assignment.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
public class ConstraintErrorAdvice {
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleConstraintValidateError(
            ConstraintViolationException exception
    ) {
        String messageTemplate = getViolatedMessage(exception);
        return new ErrorResponse(messageTemplate);
    }

    private String getViolatedMessage(ConstraintViolationException exception) {
        String messageTemplate = null;
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            messageTemplate = violation.getMessageTemplate();
        }
        return messageTemplate;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleConstraintValidateError(
            MethodArgumentNotValidException exception
    ) {
        String defaultMessage = null;
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
             defaultMessage = fieldError.getDefaultMessage();
        }
        return new ErrorResponse(defaultMessage);
    }
}
