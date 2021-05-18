package net.tack.school.notes.errorhandler;

import lombok.extern.slf4j.Slf4j;
import net.tack.school.notes.dto.ErrorResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse handleErrorValidation(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = new ErrorResponse();

        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            final ServerError error = new ServerError();
            error.setErrorCode(fieldError.getCode());
            error.setMessage(fieldError.getDefaultMessage());
            error.setField(fieldError.getField());
            errorResponse.add(error);
        });

        log.error(ExceptionUtils.getMessage(e));
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResponseStatusException.class)
    @ResponseBody
    public ErrorResponse handleResponseStatusException(ResponseStatusException e) {
        ErrorResponse errorResponse = new ErrorResponse();

        final ServerError error = new ServerError();
        error.setErrorCode(e.getStatus().toString());
        error.setMessage(e.getCause().getMessage());
        error.setField(e.getReason());
        errorResponse.add(error);

        log.error(ExceptionUtils.getMessage(e));
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestCookieException.class)
    @ResponseBody
    public ErrorResponse handlMissingRequestCookieException(MissingRequestCookieException e) {
        ErrorResponse errorResponse = new ErrorResponse();

        final ServerError error = new ServerError();
        error.setErrorCode("400");
        error.setMessage(e.getMessage());
        error.setField(e.getCookieName());
        errorResponse.add(error);

        return errorResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public ErrorResponse handleErrorSql(SQLException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        final ServerError error = new ServerError();

        error.setErrorCode("400");
        error.setMessage(ExceptionUtils.getMessage(e) + " SQL error code " + String.valueOf(e.getErrorCode()));
        error.setField("UnknownField. Something wrong with request. Read the message below");
        errorResponse.getErrors().add(error);

        log.error(e.getMessage() + e.getNextException());
        return errorResponse;
    }

    public static class ServerError {
        private String errorCode;
        private String field;
        private String message;

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
