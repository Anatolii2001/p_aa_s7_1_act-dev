package app;

import app.entities.*;
import app.services.RoleService;
import app.services.UserService;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for AdminRestController with MockMVC
 * @author Komarov Rostislav
 */

@SpringBootTest
@AutoConfigureMockMvc
public class AdminApplicationTests {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();
    private ArrayList<Role> roles;

    @BeforeEach
    public void addRolesToDatabase() {
        Role admin = new Role("ROLE_ADMIN");
        Role manager = new Role("ROLE_MANAGER");
        Role passenger = new Role("ROLE_PASSENGER");
        roles = new ArrayList<Role>();
        roles.add(admin);
        roles.add(manager);
        roles.add(passenger);
        roles.forEach(r -> roleService.saveRole(r));
    }

    @AfterEach
    public void deleteRolesFromDatabase() {
        userService.deleteAll();
        roleService.deleteRoleAll();
        roles.clear();
    }

    // tests for findAllUsers GET "/users"

    /**
     * Method testing getting every user existing in DB, adding three users before request
     * successful request have to throw 200 OK status with content type application_json and with users themselves
     * @throws Exception
     */
    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void givenThreeUsers_whenGettingAllUsers_thenStatus200andUsersReturned() throws Exception {
        Admin admin = createTestAdminUser_thenAddToDatabase();
        AirlineManager manager = createTestManagerUser_thenAddToDatabase();
        Passenger passenger = createTestPassengerUser_thenAddToDatabase();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(List.of(admin, manager, passenger))));
    }

    /**
     * Method testing case when fetching users without any existing there.
     * Have to throw 204 No Content status and "[]" in response body
     * @throws Exception
     */
    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void gettingAllUsers_whenNoUserInDatabase_thenStatus204NoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/users"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    /**
     * Method testing case when trying to get all users not being an admin
     * Have to throw 403 status
     * @throws Exception
     */
    @Test
    @WithMockUser(roles = {"PASSENGER"})
    public void gettingAllUsers_whenUserIsNotAdmin_thenStatus403forbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/users"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    // findUserById GET "/users/{id}"

    /**
     * Adding three users before request
     * Method testing case when admin getting user by specific ID
     * Have to return 200 status OK and user object itself
     * @throws Exception
     */
    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void givenThreeUsers_whenGettingProperUserById_thenStatus200andUserReturned() throws Exception {
        Passenger passenger = createTestPassengerUser_thenAddToDatabase();
        Long passengerId = passenger.getId();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/users/{id}", passengerId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(passenger)));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void gettingWrongUser_thenStatus404NotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/users/54"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void gettingUserWithWrongParamFormat_thenStatus400BadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/users/stringInsteadOfNumber"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"PASSENGER"})
    public void givenThreeUsers_gettingUser_whenUserIsNotAdmin_thenStatus403forbidden() throws Exception {
        Passenger passenger = createTestPassengerUser_thenAddToDatabase();
        Long passengerId = passenger.getId();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/users/{id}", passengerId))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    // createAdmin POST "/"

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void creatingAdmin_thenStatus200andSavedInstanceReturned() throws Exception {
        Admin admin = new Admin();
        admin.setPassword("123");
        admin.setEmail("admintest@gmail.com");
        admin.setRoles(List.of(roleService.findRoleByName("ROLE_ADMIN")));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin")
                        .content(mapper.writeValueAsString(admin))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(admin)));
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    public void creatingAdminWithNoAdminRole_thenStatus403Forbidden() throws Exception {
        Admin admin = new Admin();
        admin.setPassword("123");
        admin.setEmail("admintest@gmail.com");
        admin.setRoles(List.of(roleService.findRoleByName("ROLE_ADMIN")));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin")
                        .content(mapper.writeValueAsString(admin))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    // createManager POST "/manager"

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void creatingManager_thenStatus200andSavedInstanceReturned() throws Exception {
        AirlineManager manager = new AirlineManager();
        manager.setPassword("123");
        manager.setEmail("managertest@gmail.com");
        manager.setRoles(List.of(roleService.findRoleByName("ROLE_ADMIN")));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/manager")
                        .content(mapper.writeValueAsString(manager))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(manager)));
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    public void creatingManagerWithNoAdminRole_thenStatus403Forbidden() throws Exception {
        AirlineManager manager = new AirlineManager();
        manager.setPassword("123");
        manager.setEmail("managertest@gmail.com");
        manager.setRoles(List.of(roleService.findRoleByName("ROLE_ADMIN")));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/manager")
                        .content(mapper.writeValueAsString(manager))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // updateUser PUT "/users/{id}"

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void givenOneUser_whenUpdatingUser_thenStatus200andUpdatedUserReturned() throws Exception {
        Passenger passenger = createTestPassengerUser_thenAddToDatabase();
        User target = userService.findById(passenger.getId());
        target.setEmail("updatedmail@gmail.com");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/users/{id}", passenger.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(target)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(target)));
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    public void updatingUserWithNoAdminRole_thenStatus403Forbidden() throws Exception {
        Passenger passenger = createTestPassengerUser_thenAddToDatabase();
        User target = userService.findById(passenger.getId());
        target.setEmail("updatedmail@gmail.com");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/users/{id}", passenger.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(target)))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    // deleteUser DELETE "/users/{id}"

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void givenOneUser_whenDeletingUser_thenStatus200andDeletedUserReturned() throws Exception {
        Passenger passenger = createTestPassengerUser_thenAddToDatabase();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/users/{id}", passenger.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(passenger)));
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    public void deletingUserWithNoAdminRole_thenStatus403Forbidden() throws Exception {
        Passenger passenger = createTestPassengerUser_thenAddToDatabase();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/users/{id}", passenger.getId()))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    // findAllRoles GET "/roles"

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void givenThreeRoles_whenGettingAllRoles_thenStatus200andRolesReturned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/roles"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(roles)));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void givenNoRoles_whenGettingAllRoles_thenStatus204NoContentReturned() throws Exception {
        roleService.deleteRoleAll();
        roles.clear();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/roles"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    public void givenThreeRoles_whenGettingAllRolesNotAsAdmin_thenStatus403forbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/roles"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    // createRole POST "/roles"

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void creatingRole_thenStatus200andNewRoleReturned() throws Exception {
        String value = "PILOT";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/roles")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(value)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(value)));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void creatingRoleWithValueNull_thenStatus400BadRequestReturned() throws Exception {
        String value = null;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(value)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    public void creatingRoleNotAsAdmin_thenStatus403forbidden() throws Exception {
        String value = null;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(value)))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    // findRoleById GET "/roles/{id}"

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void gettingRoleByProperId_thenStatus200andRoleItselfReturned() throws Exception {
        Long testId = roles.get(1).getId();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/{id}", testId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(roles.get(1))));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void gettingNotExistingRoleByProperId_thenStatus404NotFoundReturned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/99999999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void gettingRoleByNotProperId_thenStatus400BadRequestReturned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/IdCantBeAStringValue"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    public void gettingRoleByProperIdNotAsAdmin_thenStatus403forbiddenReturned() throws Exception {
        Long testId = roles.get(1).getId();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/{id}", testId))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    // deleteRole DELETE "/roles/{id}"

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void deletingRole_thenStatus200andDeletedRoleReturned() throws Exception {
        Role testRole = roleService.findRoleById(roles.get(1).getId());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/roles/{id}", testRole.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(testRole)));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void deletingNotExistingRole_thenStatus404NotFoundReturned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/roles/745674"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void deletingRoleByNotProperId_thenStatus400BadRequestReturned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/roles/IdCantBeAStringValue"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    public void deletingRoleByProperIdNotAsAdmin_thenStatus403forbiddenReturned() throws Exception {
        Long testId = roles.get(1).getId();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/roles/{id}", testId))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    /**
     * Method creating new Admin instance fetch it into DB and return it
     * @return {@link app.entities.Admin}
     */
    private Admin createTestAdminUser_thenAddToDatabase() {
        Admin admin = new Admin();
        admin.setPassword("adminpassword");
        admin.addRoleToCollection(roleService.findRoleByName("ROLE_ADMIN"));
        userService.saveUser(admin);
        return admin;
    }

    /**
     * Method creating new AirlineManager instance fetch it into DB and return it
     * @return {@link app.entities.AirlineManager}
     */
    private AirlineManager createTestManagerUser_thenAddToDatabase() {
        AirlineManager manager = new AirlineManager();
        manager.setPassword("adminpassword");
        manager.addRoleToCollection(roleService.findRoleByName("ROLE_ADMIN"));
        userService.saveUser(manager);
        return manager;
    }

    /**
     * Method creating new Passenger instance fetch it into DB and return it
     * @return {@link app.entities.Passenger}
     */
    private Passenger createTestPassengerUser_thenAddToDatabase() {
        Passenger passenger = new Passenger();
        passenger.setPassword("adminpassword");
        passenger.addRoleToCollection(roleService.findRoleByName("ROLE_ADMIN"));
        userService.saveUser(passenger);
        return passenger;
    }

}
