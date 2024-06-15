package itacademy.misbackend.repo;

import itacademy.misbackend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByNameIgnoreCase(String name);

    boolean existsByName(String name);
}
