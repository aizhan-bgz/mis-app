package itacademy.misbackend.repo;

import itacademy.misbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsernameOrEmail(String username, String email);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
    User findByDeletedAtIsNullAndDeletedByIsNullAndUsername(String username);
    List<User> findAllByDeletedAtIsNullAndDeletedByIsNull();
    User findByDeletedAtIsNullAndDeletedByIsNullAndId(Long id);
}
