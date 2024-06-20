package itacademy.misbackend.exception;

import itacademy.misbackend.dto.CustomResponseMessage;
import itacademy.misbackend.dto.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponseMessage<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorMessage errorMessage = new ErrorMessage();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorMessage.addError(fieldName, message);
        });
        log.error(e.getMessage());
        return new CustomResponseMessage<>(
                         errorMessage,
                "Ошибка валидации.",
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
        log.error(String.valueOf(e.getErrorMessage()));
        return new CustomResponseMessage<>(
                e.getErrorMessage(),
                null,
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler(ConfirmationCodeMismatchException.class)
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
