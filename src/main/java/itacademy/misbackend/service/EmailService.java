package itacademy.misbackend.service;

import itacademy.misbackend.dto.RegistrationMessage;

public interface EmailService {
    void sendRegistrationMessage(RegistrationMessage message);

}
