package app.services;

import app.entities.Role;
import app.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * В этом классе сервис для сущности Роль
 *
 * @author Minibaeva Elvira
 */
@AllArgsConstructor
@Service
public class RoleService {
    private RoleRepository roleRepository;

    public Role findRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }

    public Role findRoleById(Long id) {
        return roleRepository.findRoleById(id);
    }

    public void deleteRoleById(Long id) {
        roleRepository.deleteById(id);
    }

    public void saveRole(Role role) {
        roleRepository.save(role);

    }

    public List<Role> findAll() {
        return roleRepository.findAll();

    }
    public void deleteRoleAll(){
        roleRepository.deleteAll();
    }


}
