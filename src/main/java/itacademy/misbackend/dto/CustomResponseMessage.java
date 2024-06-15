package itacademy.misbackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import itacademy.misbackend.enums.ResultCode;
import itacademy.misbackend.enums.ResultCodeAPI;
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
    String resultCode;
    String details;
    String message;
    int code;

    public CustomResponseMessage(T result, ResultCodeAPI resultCode, String details, String message, ResultCode code) {
        this.result = result;
        this.resultCode = resultCode.getDescription();
        this.details = details;
        this.message = message;
        this.code = code.getHttpCode();
    }
}
