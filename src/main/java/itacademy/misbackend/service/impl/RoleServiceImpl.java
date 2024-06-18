package itacademy.misbackend.service.impl;

import itacademy.misbackend.entity.Role;
import itacademy.misbackend.exception.DuplicateValueException;
import itacademy.misbackend.exception.NotFoundException;
import itacademy.misbackend.repo.RoleRepo;
import itacademy.misbackend.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final RoleRepo roleRepo;
    @Transactional
    @Override
    public Long save(Role role) {
        if (roleRepo.existsByName(role.getName())) {
            throw new DuplicateKeyException("Роль с таким названием уже существует");
        }
        role.setName(role.getName().toUpperCase());
        roleRepo.save(role);
        return role.getId();
    }

    @Override
    public List<Role> findAll() {
        List<Role> roles = roleRepo.findAll();
        if (roles.isEmpty()) {
            throw new NotFoundException("Роли не найдены");
        } else {
            return roles;
        }
    }

    @Override
    public Role findById(Long id) {
        Optional<Role> optionalRole = roleRepo.findById(id);
        if (optionalRole.isPresent()) {
            return optionalRole.get();
        } else {
            throw new NotFoundException("Роль с id " + id + " не найдена");
        }
    }

    @Override
    public Role findByNameIgnoreCase(String name) {
        if (roleRepo.findByNameIgnoreCase(name) == null) {
            throw new NotFoundException("Роль с наименованием " + name + " не найдена");
        } else {
            return roleRepo.findByNameIgnoreCase(name);
        }
    }
    @Transactional
    @Override
    public Role update(Long id, Role role) {
        Optional<Role> optionalRole = roleRepo.findById(id);
        if (optionalRole.isPresent()) {
            optionalRole.get().setName(role.getName().toUpperCase());
            roleRepo.save(optionalRole.get());
            return optionalRole.get();
        } else {
            throw new NotFoundException("Роль с id " + id + " не найдена");
        }
    }

    @Override
    public String delete(Long id) {
        Optional<Role> optionalRole = roleRepo.findById(id);
        if (optionalRole.isPresent()) {
            roleRepo.deleteById(id);
            return "Роль успешно удалена";
        } else {
            throw new NotFoundException("Роль с id " + id + " не найдена");
        }
    }
}
