package itacademy.misbackend.service.impl;

import itacademy.misbackend.entity.Role;
import itacademy.misbackend.exception.NotFoundException;
import itacademy.misbackend.repo.RoleRepo;
import itacademy.misbackend.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.PropertyValueException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepo roleRepo;
    @Transactional
    @Override
    public Long save(Role role) {
        if (role.getName().isEmpty() || role.getName().isBlank() || role.getName() == null) {
            throw new PropertyValueException("Role", "name", " Наименование роли не может быть пустым");
        } else {
            role.setName(role.getName().toUpperCase());
            Role savedRole = roleRepo.save(role);
            return savedRole.getId();
        }
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
            if (role.getName().isEmpty()){
                throw new PropertyValueException("Наименование роли не может быть пустым", "Role", "name");
            } else {
                optionalRole.get().setName(role.getName().toUpperCase());
                roleRepo.save(optionalRole.get());
                return optionalRole.get();
            }
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
