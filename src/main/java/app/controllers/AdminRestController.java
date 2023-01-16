package app.controllers;


import app.entities.Admin;
import app.entities.AirlineManager;
import app.entities.Role;
import app.entities.User;
import app.services.RoleService;
import app.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RestController класса Admin "api/admin"
 * getCurrentUser
 * findAllUsers GET "/users"
 * findUserById GET "/users/{id}"
 * create Admin POST "/"
 * createManager POST "/manager"
 * updateUser PUT "/users/{id}"
 * deleteUser DELETE "/users/{id}"
 * findAllRoles GET "/roles"
 * createRole POST "/roles"
 * findRoleById GET "/roles/{id}"
 * deleteRole DELETE "/roles/{id}"
 *
 * @author Minibaeva Elvira
 */

@AllArgsConstructor
@RestController
@RequestMapping("/api")
@Api(tags = "AdminRestController", description = "Управление пользователями и ролями")
public class AdminRestController {

static final Logger logger = LogManager.getLogger(AdminRestController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;


    /** Метод возвращающий авторизированного пользователя
     *
     * @return user
     */
    @ApiOperation(value = "Get authorized User")
    @GetMapping()
    public ResponseEntity<User> getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("Show authorized User");
        return ResponseEntity.ok(user);}

    /**Метод возвращающий лист всех пользователей
     *
     * @return List of Users
     */
    @ApiOperation(value = "Get list of all Users")
    @GetMapping("/users")
    public ResponseEntity<List<User>> findAllUsers() {
        logger.info("Show list of all Users");
        return ResponseEntity.ok(userService.findAll());
    }

    /**Метод возвращающий пользователя по id
     *
     * @param id
     * @return User
     */
    @ApiOperation(value = "Get user by id")
    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable("id") long id) {
        logger.info("Show user by id");
        return ResponseEntity.ok(userService.findById(id));
    }

    /**Метод создающий нового Админа
     *
     * @param admin
     */
    @ApiOperation(value = "Create new Admin")
    @PostMapping("/create/admin")
    public ResponseEntity<Void> createAdmin(@RequestBody Admin admin) {
        if(admin.getRoles().isEmpty()) {
            admin.addRoleToCollection(roleService.findRoleByName("ROLE_ADMIN"));
        }
        userService.saveUser(admin);
        logger.info("Admin created");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**Метод создающий нового Менеджера
     *
     * @param manager
     */
    @ApiOperation(value = "Create new AirlineManager")
    @PostMapping("/create/manager")
    public ResponseEntity<Void> createManager(@RequestBody AirlineManager manager) {
        if (manager.getRoles().isEmpty()) {
            manager.addRoleToCollection(roleService.findRoleByName("ROLE_MANAGER"));
        }
        userService.saveUser(manager);
        logger.info("New Admin created");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**Метод обновляющий данные пользователей
     *
     * @param user
     */
    @ApiOperation(value = "Edit user")
    @PutMapping("/edit")
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        userService.saveUserFromController(user);
        logger.info("User edited");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**Метод удаляющий пользователя по id
     *
     * @param id
     */
    @ApiOperation(value = "Delete user by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) {
        userService.deleteUserById(id);
        logger.info("User deleted: " + id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**Метод возвращающий лист всех ролей
     *
     * @return List of Roles
     */
    @ApiOperation(value = "Get list of all Roles")
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> findAllRoles() {
        logger.info("Show list of all Roles");
        return ResponseEntity.ok(roleService.findAll());
    }

    /**Метод создающий новую роль
     *
     * @param role
     */
    @ApiOperation(value = "Create new Role")
    @PostMapping("/roles/create")
    public ResponseEntity<Void> createRole(@RequestBody Role role) {
        roleService.saveRole(role);
        logger.info("New Role created");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**Метод возвращающий роль по id
     *
     * @param id
     * @return Role
     */
    @ApiOperation(value = "Get role by id")
    @GetMapping("/roles/{id}")
    public ResponseEntity<Role> findRoleById(@PathVariable("id") long id) {
        logger.info("Show role: " + id);
        return ResponseEntity.ok(roleService.findRoleById(id));
    }

    /**Метод удаляющий роль по id
     * Метод позволяет удалить только не используемую роль
     *
     * @param id
     */
    @ApiOperation(value = "Delete role by id")
    @DeleteMapping("/roles/delete/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable("id") long id) {
        logger.info("Role deleted: " + id);
        roleService.deleteRoleById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}