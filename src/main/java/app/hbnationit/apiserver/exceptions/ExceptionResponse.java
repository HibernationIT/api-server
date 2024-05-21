package app.hbnationit.apiserver.exceptions;

import lombok.Getter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionResponse {
    protected final Log logger = LogFactory.getLog(getClass());
    private final HttpStatus status;
    private final Exception exception;

    public ExceptionResponse(HttpStatus status, Exception exception) {
        this.status = status;
        this.exception = exception;
    }

    public static @Getter class Body {
        private final Integer status;
        private final String message;
        private final String error;

        public Body(Integer status, String message, String error) {
            this.status = status;
            this.message = message;
            this.error = error;
        }
    }

    public ResponseEntity<Body> response() {
        logger.info(log(exception));
        Body body = new Body(status.value(),status.getReasonPhrase(),exception.getMessage());
        return ResponseEntity.status(status).body(body);
    }

    protected String log(Exception exception) {
        return "[" + exception.getClass().getSimpleName() + "] " +
                exception.getStackTrace()[0].toString();
    }
}
