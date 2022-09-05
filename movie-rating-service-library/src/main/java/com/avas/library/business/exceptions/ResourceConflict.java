package com.avas.library.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;


@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceConflict extends RuntimeException {
    private Long resourceId;
    private Long resourceId2;
    private String message;
    public ResourceConflict(String message, Long resourceId) {
        super(message);
        this.message = message;
        this.resourceId = resourceId;
    }
    public ResourceConflict(String message, Long resourceId, Long resourceId2) {
        super(message);
        this.message = message;
        this.resourceId = resourceId;
        this.resourceId2 = resourceId2;
    }

    public ResourceConflict(String message) {
        super(message);
    }

    @Override
    public String getLocalizedMessage()
    {
        return (resourceId==null)?
                message: !(resourceId2 == null)?
                MessageFormat.format(message, resourceId, resourceId2): MessageFormat.format(message, resourceId);
    }
}
