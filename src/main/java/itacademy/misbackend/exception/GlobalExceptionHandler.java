package itacademy.misbackend.exception;

import itacademy.misbackend.dto.CustomResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponseMessage<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        return new CustomResponseMessage<>(
                null,
                "Ошибка валидации: " + e.getBindingResult().getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining(", ")),
                 HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomResponseMessage<Object> handleNotFoundException(NotFoundException e) {
        log.error(e.getMessage());
        return new CustomResponseMessage<>(
                null,
                e.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
    }

    @ExceptionHandler(DuplicateValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponseMessage<Object> handleDuplicateValueException(DuplicateValueException e) {
        log.error(e.getMessage());
        return new CustomResponseMessage<>(
                null,
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler(DuplicateValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponseMessage<Object> handleConfirmationCodeMismatchException(ConfirmationCodeMismatchException e) {
        log.error(e.getMessage());
        return new CustomResponseMessage<>(
                null,
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CustomResponseMessage<Object> handleGenericException(Exception e){
        log.error(e.getMessage());
        return new CustomResponseMessage<>(
                null,
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
    }
}
