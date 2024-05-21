package app.hbnationit.apiserver.exceptions;

import io.netty.handler.codec.MessageAggregationException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    protected Log logger = LogFactory.getLog(getClass());

    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<ExceptionResponse.Body> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return new ExceptionResponse(HttpStatus.UNAUTHORIZED, e).response();
    }

    @ExceptionHandler(MessageAggregationException.class)
    protected ResponseEntity<ExceptionResponse.Body> handleMessagingException(MessageAggregationException e) {
        return new ExceptionResponse(HttpStatus.BAD_REQUEST, e).response();
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    protected ResponseEntity<ExceptionResponse.Body> handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException e) {
        return new ExceptionResponse(HttpStatus.UNAUTHORIZED, e).response();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ExceptionResponse.Body> handleEntityNotFoundException(EntityNotFoundException e) {
        return new ExceptionResponse(HttpStatus.NOT_FOUND, e).response();
    }

    @ExceptionHandler(EntityExistsException.class)
    protected ResponseEntity<ExceptionResponse.Body> handleEntityExistsException(EntityExistsException e) {
        return new ExceptionResponse(HttpStatus.CONFLICT, e).response();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ExceptionResponse.Body> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ExceptionResponse(HttpStatus.BAD_REQUEST, e).response();
    }

}
