package itacademy.misbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private Map<String, String> errors = new HashMap<>();

    public void addError(String fieldName, String message){
        errors.put(fieldName, message);
    }
}
