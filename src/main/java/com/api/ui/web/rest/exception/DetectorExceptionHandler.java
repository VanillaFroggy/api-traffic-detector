package com.api.ui.web.rest.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class DetectorExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException exception) {
        log.error("Список детекторов пока что пуст", exception);
        return new ResponseEntity<>("Список детекторов пока что пуст", HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleNullPointerException(NullPointerException exception) {
        log.error("Ошибка в параметрах запроса. Запрос не следует повторять", exception);
        return new ResponseEntity<>(
                "Ошибка в параметрах запроса. Запрос не следует повторять",
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
        log.error("Ошибка сервера при выполнении запроса. Запрос следует повторить позднее", exception);
        return new ResponseEntity<>(
                "Ошибка сервера при выполнении запроса. Запрос следует повторить позднее",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
