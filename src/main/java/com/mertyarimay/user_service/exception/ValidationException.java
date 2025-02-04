package com.mertyarimay.user_service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationException extends ProblemDetails {
    private Map<String,String>validationErrors;
}
