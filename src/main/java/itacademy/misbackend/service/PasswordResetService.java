package itacademy.misbackend.service;

import itacademy.misbackend.dto.UserPasswordReset;

public interface PasswordResetService {
    void initiatePasswordReset(String email);
    void resetPassword(UserPasswordReset userPasswordReset);
}
