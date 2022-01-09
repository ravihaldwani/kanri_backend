package com.kanrisoft.kanri.user;

import com.kanrisoft.kanri.user.exception.EmailAlreadyUsedException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmailAlreadyUsedException.class)
    ResponseEntity<Object> handleEmailAlreadyExists(EmailAlreadyUsedException ex) {
        var body = new ApiError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }
}

@AllArgsConstructor
@Getter
class ApiError {
    private String errorMessage;
}
