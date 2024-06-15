package itacademy.misbackend.service;

import itacademy.misbackend.entity.Role;

import java.util.List;

public interface RoleService {
    Long save(Role role);
    List<Role> findAll();
    Role findById(Long id);
    Role findByNameIgnoreCase(String name);
    Role update(Long id, Role role);
    String delete(Long id);
}