package ru.clevertec.cheque.controller;

import ru.clevertec.cheque.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@ControllerAdvice
public class ProjectExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiCallError handleExceptionServerError(
            HttpServletRequest request,
            Exception ex
    ) {
        log.error("handleServiceExceptionServerError {}\n", request.getRequestURI(), ex);
        return new ApiCallError(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiCallDetailedError<?> handleMethodArgumentNotValid(
            HttpServletRequest request,
            MethodArgumentNotValidException ex
    ) {
        List<Map<String, String>> details = new ArrayList<>();
        Map<String, String> detail = new HashMap<>();
        detail.put("field", ex.getBindingResult().getFieldError().getField());
        detail.put("value", ex.getBindingResult().getFieldError().getRejectedValue().toString());
        detail.put("info", ex.getFieldError().getDefaultMessage());
        details.add(detail);
        log.error("handleServiceExceptionServerError {}\n", request.getRequestURI(), ex);
        return new ApiCallDetailedError<>("Validation failed", details);
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiCallDetailedError<?> handleServiceExceptionServerError(
            HttpServletRequest request,
            ServiceException ex
    ) {
        log.error("handleServiceExceptionServerError {}\n", request.getRequestURI(), ex);
        List<Map<String, String>> details = new ArrayList<>();
        Map<String, String> detail = new HashMap<>();
        detail.put("info", ex.getMessage());
        details.add(detail);
        return new ApiCallDetailedError<>("Parameters are not valid", details);
    }

    @Data
    @AllArgsConstructor
    public static class ApiCallDetailedError<T> {
        private String message;
        private List<T> details;
    }

    @Data
    @AllArgsConstructor
    public static class ApiCallError {
        private String message;
    }
}
