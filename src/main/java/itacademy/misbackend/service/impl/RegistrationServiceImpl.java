package itacademy.misbackend.service.impl;

import itacademy.misbackend.dto.ConfirmRequest;
import itacademy.misbackend.dto.EmailMessage;
import itacademy.misbackend.dto.UserDoctorRequest;
import itacademy.misbackend.dto.UserPatientRequest;
import itacademy.misbackend.entity.User;
import itacademy.misbackend.exception.ConfirmationCodeMismatchException;
import itacademy.misbackend.repo.UserRepo;
import itacademy.misbackend.service.RegistrationService;
import itacademy.misbackend.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final EmailServiceImpl emailService;
    private final UserService userService;
    private final UserRepo userRepo;
    @Transactional
    @Override
    public void registerDoctor(UserDoctorRequest userDoctorRequest) {
        UserDoctorRequest user = userService.createDoctor(userDoctorRequest);

        EmailMessage message = new EmailMessage();
        message.setEmail(user.getUser().getEmail());
        message.setConfirmCode(user.getUser().getConfirmCode());
        emailService.sendRegistrationMessage(message);
    }
    @Transactional
    @Override
    public void registerPatient(UserPatientRequest userPatientRequest) {
        UserPatientRequest user = userService.createPatient(userPatientRequest);
        EmailMessage message = new EmailMessage();
        message.setEmail(user.getUser().getEmail());
        message.setConfirmCode(user.getUser().getConfirmCode());
        emailService.sendRegistrationMessage(message);
    }

    @Override
    public void confirm(ConfirmRequest confirmRequest) {
        User user = userRepo.findByEmail(confirmRequest.getEmail());
        if (!user.getConfirmCode().equals(confirmRequest.getConfirmCode())) {
            throw new ConfirmationCodeMismatchException("Неверный код подтверждения");
        }
        user.setEnabled(true);
        userRepo.save(user);
    }
}
