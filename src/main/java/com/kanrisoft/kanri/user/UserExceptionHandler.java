package com.kanrisoft.kanri.user;

import com.kanrisoft.kanri.user.exception.EmailAlreadyUsedException;
import com.kanrisoft.kanri.user.exception.InvalidEmailException;
import com.kanrisoft.kanri.user.exception.InvalidRequestException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

interface ApiError {
}

@Value
class SingleApiError implements ApiError {
    String message;
}

@Value
class MultipleApiError implements ApiError {
    String message;
    List<String> groupMessages;
}

@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EmailAlreadyUsedException.class, AuthenticationException.class})
    ResponseEntity<Object> handleEmailAlreadyExists(EmailAlreadyUsedException ex) {
        var body = ErrorResponse.ofSingle(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler({InvalidRequestException.class, InvalidEmailException.class})
    ResponseEntity<Object> handleInvalidRequestException(RuntimeException ex) {
        var body = ErrorResponse.ofSingle(ex.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> handleException(Exception ex) {
        var body = ErrorResponse.ofSingle(ex.getMessage());
        return ResponseEntity.internalServerError().body(body);
    }
}

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
class ErrorResponse {
    ApiError error;

    static ErrorResponse ofSingle(String message) {
        var msg = new SingleApiError(message);
        return new ErrorResponse(msg);
    }

    static ErrorBuilder forMulti(String message) {
        return new ErrorBuilder(message);
    }

    static class ErrorBuilder {
        private final List<String> subMessages;
        private final String message;

        public ErrorBuilder(String message) {
            this.message = message;
            this.subMessages = List.of();
        }

        ErrorBuilder addMessage(String message) {
            subMessages.add(message);
            return this;
        }

        ErrorResponse build() {
            var messages = new MultipleApiError(message, subMessages);
            return new ErrorResponse(messages);
        }
    }
}

