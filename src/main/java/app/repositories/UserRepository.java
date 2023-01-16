package app.repositories;


import app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для User нужен для SpringSecurity
 *
 * @author Minibaeva Elvira
 */

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    User findUserById(Long id);
    List<User> findAll();

}
