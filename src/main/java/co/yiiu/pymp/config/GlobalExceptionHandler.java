package co.yiiu.pymp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<String> defaultErrorHandler(Exception e) {
        log.error(e.getMessage());
        String message = e.getMessage();
        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) e;
            message = StringUtils.collectionToCommaDelimitedString(
                    constraintViolationException.getConstraintViolations()
                            .stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.toList()));
        } else if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            message = StringUtils.collectionToCommaDelimitedString(
                    bindException.getAllErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .collect(Collectors.toList())
            );
        }
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(message);
    }
}