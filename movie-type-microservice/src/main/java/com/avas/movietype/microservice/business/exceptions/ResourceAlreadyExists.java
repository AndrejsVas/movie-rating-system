package com.avas.movietype.microservice.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;


@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyExists extends RuntimeException {
    private Long resourceId;
    private String message;

    public ResourceAlreadyExists(String message, Long resourceId) {
        super(message);
        this.message = message;
        this.resourceId = resourceId;
    }

    public ResourceAlreadyExists(String message) {
        super(message);
    }

    @Override
    public String getLocalizedMessage() {
        if (resourceId == null)
            return message;
        return MessageFormat.format(message, resourceId);
    }
}