package itacademy.misbackend.service;

public interface PasswordResetService {
    void initiatePasswordReset(String email);
    void resetPassword(String token, Long id, String newPassword);
}
