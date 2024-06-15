package itacademy.misbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedUser {
    private Long id;
    private String username;
    private Map<String, String> tokens;
}
