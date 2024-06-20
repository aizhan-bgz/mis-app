package itacademy.misbackend.exception;

import itacademy.misbackend.dto.ErrorMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DuplicateValueException extends RuntimeException {
    private ErrorMessage errorMessage;

    public DuplicateValueException(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}
