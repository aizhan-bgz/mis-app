package itacademy.misbackend.service.impl;

import itacademy.misbackend.dto.RegistrationMessage;
import itacademy.misbackend.dto.UserDoctorRequest;
import itacademy.misbackend.dto.UserPatientRequest;
import itacademy.misbackend.entity.User;
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

        RegistrationMessage message = new RegistrationMessage();
        message.setEmail(user.getUser().getEmail());
        message.setVerificationToken(user.getUser().getVerificationToken());
        emailService.sendRegistrationMessage(message);
    }
    @Transactional
    @Override
    public void registerPatient(UserPatientRequest userPatientRequest) {
        UserPatientRequest user = userService.createPatient(userPatientRequest);
        RegistrationMessage message = new RegistrationMessage();
        message.setEmail(user.getUser().getEmail());
        message.setVerificationToken(user.getUser().getVerificationToken());
        emailService.sendRegistrationMessage(message);
    }

    @Override
    public void confirm(String token, Long id) {
        User user = userRepo.findByDeletedAtIsNullAndDeletedByIsNullAndId(id);
        if (user.getVerificationToken().equals(token)) {
            user.setEnabled(true);
            userRepo.save(user);
        }
    }
}
