package app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
/**
 * Сущность User нужен для SpringSecurity
 * Сущность User является родительской для классов: Admin, AirlineManager, Passenger.
 * Для хранения пользователей выбрана стратегия Joined.
 *
 * @author Minibaeva Elvira
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "email", unique = true)
    @Email
    @NotEmpty
    private String email;
    @Column(name = "password")
    @NotEmpty
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn( name= "user_id"),
            inverseJoinColumns = @JoinColumn( name= "role_id"))
    private Collection<Role> roles = new HashSet<>();

    /**
     * Метод для добавления роли в коллекцию ролей
     * @param role
     */
    public void addRoleToCollection(Role role) {
        roles.add(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    /**
     * Конструктор, нужен для аутентификации, используется в UserService
     * в методе loadUserByUsername()
     * @param email
     * @param password
     * @param roles
     */
    public User(String email, String password, Collection<Role> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
