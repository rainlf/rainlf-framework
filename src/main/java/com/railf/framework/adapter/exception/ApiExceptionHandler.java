package com.railf.framework.adapter.exception;

import com.railf.framework.adapter.dto.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author : rain
 * @date : 2020/6/1 12:18
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIResponse<String> exceptionHandler(Exception e) {
        log.error("Handle Exception", e);
        return APIResponse.error(e.getMessage());
    }
}
