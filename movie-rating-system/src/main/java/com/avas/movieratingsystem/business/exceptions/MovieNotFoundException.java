package com.avas.movieratingsystem.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MovieNotFoundException extends RuntimeException {
    private long movieId;
    private String message;
    public MovieNotFoundException(String message, Long movieId ) {
        super();
        this.message = message;
        this.movieId = movieId;
    }

    @Override
    public String getLocalizedMessage()
    {

        return MessageFormat.format(message, movieId);
    }
}