package com.waseel.pbm.authentication.controller.exceptions;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.waseel.pbm.authentication.model.Error;
import com.waseel.pbm.authentication.service.ErrorResponseService;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    MessageSource messageSource;

    @Autowired
    private ErrorResponseService errorResponseService;

     private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorId = UUID.randomUUID().toString();

        Error error = errorResponseService.getErrorObject(ex.getFieldError().getDefaultMessage(),
                List.of("fields." + ex.getFieldError().getField()), ex.getFieldError().getField());
        logger.error("MethodArgumentNotValid",ex);
        return ResponseEntity.badRequest()
                .body(Map.of("error", error, "errorId", errorId));
    }
}
