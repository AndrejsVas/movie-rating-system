package com.avas.movieratingsystem.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private Long resourceId;
    private String message;
    public ResourceNotFoundException(String message, Long resourceId) {
        super(message);
        this.message = message;
        this.resourceId = resourceId;
    }
    public ResourceNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getLocalizedMessage()
    {
        if(resourceId == null)
            return message;
        return MessageFormat.format(message, resourceId);
    }
}
