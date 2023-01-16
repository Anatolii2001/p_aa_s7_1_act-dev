package app.repositories;

import app.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * В этом классе репозиторий для сущности Роль
 *
 * @author Minibaeva Elvira
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleByName(String name);

    Role findRoleById(Long id);
}
