package itacademy.misbackend.service;

public interface PasswordResetService {
    void initiatePasswordReset(String email);
    void resetPassword(String confirmCode, Long id, String newPassword);
}
