package main.com.avas.library.business.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@Log4j2
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest rq){
        log.warn(ex);
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), rq.getDescription(false));
        return new ResponseEntity<>(exceptionResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> handleResourceNotFoundException(ResourceAlreadyExists ex, WebRequest rq){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getLocalizedMessage(), rq.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ResourceAlreadyExists.class)
    public final ResponseEntity<Object> handleResourceAlreadyExistsException(ResourceAlreadyExists ex, WebRequest rq){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getLocalizedMessage(), rq.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(ResourceConflict.class)
    public final ResponseEntity<Object> handleResourceConflictException(ResourceAlreadyExists ex, WebRequest rq){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getLocalizedMessage(), rq.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }
}
