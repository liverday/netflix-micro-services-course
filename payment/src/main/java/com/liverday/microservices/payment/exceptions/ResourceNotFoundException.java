package com.liverday.microservices.payment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 484271451558222788L;

    public ResourceNotFoundException(String exception) {
        super(exception);
    }
}
