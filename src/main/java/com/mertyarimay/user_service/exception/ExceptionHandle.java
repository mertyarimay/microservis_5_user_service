package com.mertyarimay.user_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class ExceptionHandle {
    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ProblemDetails businessException(BusinessException businessException){
        ProblemDetails problemDetails=new ProblemDetails();
        problemDetails.setMessage(businessException.getMessage());
        return problemDetails;
    }
    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ProblemDetails validationProblems(MethodArgumentNotValidException methodArgumentNotValidException){
        ValidationException validationException=new ValidationException();
        validationException.setMessage("Validation Exception");
        validationException.setValidationErrors(new HashMap<>());
        for(FieldError fieldError:methodArgumentNotValidException.getBindingResult().getFieldErrors()){
            validationException.getValidationErrors().put(fieldError.getField(),fieldError.getDefaultMessage());
        }
        return validationException;

    }
}
