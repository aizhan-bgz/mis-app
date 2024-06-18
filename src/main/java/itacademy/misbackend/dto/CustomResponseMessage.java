package itacademy.misbackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomResponseMessage<T>{
    T result;
    String message;
    int code;

    public CustomResponseMessage(T result, String message, int code) {
        this.result = result;
        this.message = message;
        this.code = code;
    }
}
