package itacademy.misbackend.service.impl;

import itacademy.misbackend.dto.*;
import itacademy.misbackend.entity.Role;
import itacademy.misbackend.entity.User;
import itacademy.misbackend.exception.DuplicateValueException;
import itacademy.misbackend.exception.NotFoundException;
import itacademy.misbackend.mapper.UserMapper;
import itacademy.misbackend.repo.UserRepo;
import itacademy.misbackend.service.DoctorService;
import itacademy.misbackend.service.PatientService;
import itacademy.misbackend.service.RoleService;
import itacademy.misbackend.service.UserService;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByDeletedAtIsNullAndDeletedByIsNullAndUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь с указанным username не найден");
        }
        user.setLastAuthentication(LocalDateTime.now());
        userRepo.save(user);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
    @Override
    public UserDto save(UserDto saveUserDto) {
        log.info("СТАРТ: UserServiceImpl - create() {}", saveUserDto);
        saveUserDto.setPassword(passwordEncoder.encode(saveUserDto.getPassword()));
        saveUserDto.setConfirmCode(generateConfirmationCode());
        if (userRepo.existsByEmail(saveUserDto.getEmail()) && userRepo.existsByUsername(saveUserDto.getUsername())){
            throw new DuplicateValueException(
                            "Пользователь с указанным email (" + saveUserDto.getEmail() + ")" +
                            "и username (" + saveUserDto.getUsername() + ") уже существует."
            );
        }
        if (userRepo.existsByEmail(saveUserDto.getEmail())) {
            throw new DuplicateValueException(
                    "Пользователь с указанным email (" + saveUserDto.getEmail() + ") уже существует."
            );
        }
        if (userRepo.existsByUsername(saveUserDto.getUsername())){
            throw new DuplicateValueException(
                    "Пользователь с указанным username (" + saveUserDto.getUsername() + ") уже существует."
            );
        }
        User user = userMapper.toEntity(saveUserDto);
        var roles = new HashSet<Role>();
        user.setRoles(roles);
        user = userRepo.save(user);
        log.info("КОНЕЦ: UserServiceImpl - create {} ", userMapper.toDto(user));
        return userMapper.toDto(user);
    }

    @Transactional
    @Override
    public UserDoctorRequest createDoctor(UserDoctorRequest userDoctorRequest) {
        UserDto user = save(userDoctorRequest.getUser());
        addRole(user.getId(), "DOCTOR");
        DoctorDto doctor = userDoctorRequest.getDoctor();
        doctor.setUserId(user.getId());
        doctor = doctorService.save(doctor);
        userDoctorRequest.getUser().setId(user.getId());
        userDoctorRequest.getDoctor().setId(doctor.getId());
        return userDoctorRequest;
    }

    @Transactional
    @Override
    public UserPatientRequest createPatient(UserPatientRequest userPatientRequest) {
        UserDto user = save(userPatientRequest.getUser());
        addRole(user.getId(), "PATIENT");
        PatientDto patient = userPatientRequest.getPatient();
        patient.setUserId(user.getId());
        patient = patientService.create(patient);
        userPatientRequest.getUser().setId(user.getId());
        userPatientRequest.getPatient().setId(patient.getId());
        return userPatientRequest;
    }

    @Override
    public UserDto getById(Long id) {
        log.info("СТАРТ: UserServiceImpl - getById({})", id);
        User user = userRepo.findByDeletedAtIsNullAndDeletedByIsNullAndId(id);
        if (user == null) {
            throw new NotFoundException("Пользователь с id " + id + " не найден");
        }
        log.info("КОНЕЦ: UserServiceImpl - getById(). Пользователь - {} ", userMapper.toDto(user));
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDto> getAll() {
        log.info("СТАРТ: UserServiceImpl - getAll()");
        if (userRepo.findAllByDeletedAtIsNullAndDeletedByIsNull().isEmpty()) {
            throw new NotFoundException("Пользователей нет!");
        }
        log.info("КОНЕЦ: MedicalRecordServiceImpl - getAll()");
        return userMapper.toDtoList(userRepo.findAllByDeletedAtIsNullAndDeletedByIsNull());
    }

    @Override
    public void addRole(Long id, String roleName) {
        User user = userRepo.findByDeletedAtIsNullAndDeletedByIsNullAndId(id);
        Role role = roleService.findByNameIgnoreCase(roleName);
        if (user == null) {
            throw new NotFoundException("Пользователь с id " + id + " не найден");
        } else if (role == null) {
            throw new NotFoundException("Роль " + roleName + " не найдена");
        }
        Set<Role> roles = user.getRoles();
        if (roles == null) {
            roles = new HashSet<>();
        }
        roles.add(role);
        user.setRoles(roles);
        userRepo.save(user);
    }


    @Override
    public UserDto update(Long id, UpdatedUser userDto) {
        User user = userRepo.findByDeletedAtIsNullAndDeletedByIsNullAndId(id);
        if (user == null) {
            throw new NotFoundException("Пользователь с id " + id + " не найден");
        } else {
            if (userDto.getRoles() != null) {
                user.setRoles(userDto.getRoles());
            }
            user = userRepo.save(user);
            return userMapper.toDto(user);
        }
    }

    @Override
    public String delete(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByDeletedAtIsNullAndDeletedByIsNullAndId(id);

        if (user == null) {
            throw new NotFoundException("Пользователь с id " + id + " не найден");
        }
        user.setDeletedAt(LocalDateTime.now());
        user.setDeletedBy(authentication.getName());
        userRepo.save(user);
        return "Пользователь с id " + id + " успешно удален";
    }

    @Override
    public Long getUserIdByEmail(String email) {
        if (userRepo.existsByEmail(email)) {
            return userRepo.findByEmail(email).getId();
        } else {
            throw new NotFoundException("Пользователь с email " + email + " не найден");
        }
    }

    @Override
    public String generateConfirmationCode() {
        int code = 1000 + new Random().nextInt(9000);
        return String.valueOf(code);
    }
}
