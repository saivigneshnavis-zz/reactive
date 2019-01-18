package com.xvela.reactive.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Record not found in repository")
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
