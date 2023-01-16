package app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * Class Passenger with properties <b>email</b>,
 * <b>firstName</b>, <b>lastName</b>, <b>middleName</b>,
 * <b>phoneNumber</b>, <b>dateOfBirth</b>
 * <p>
 * создал поля пассажира, а также поле номера билета
 *
 * @author Tamara Ustyan
 */
@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Passenger")
public class Passenger extends User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "firstName")
    private String firstName;
    @NonNull
    @Column(name = "lastName")
    private String lastName;
    @NonNull
    @Column(name = "e_mail")
    private String email;
    @Column(name = "telegram")
    private long telegram;
    @NonNull
    @Column(name = "LocalDate_date_of_birth")
    private int localDateDateOfBirth;
    @NonNull
    @Column(name = "gender_of_the_Passenger")
    private int genderOfThePassenger;

    public Passenger(Long id, @Email @NotEmpty String email, long telegram, @NotEmpty String password, Collection<Role> roles,
                     @NonNull String firstName, @NonNull String lastName, @NonNull String email1,
                     @NonNull int localDateDateOfBirth, @NonNull int genderOfThePassenger) {
        super(id, email, password, roles);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email1;
        this.telegram = telegram;
        this.localDateDateOfBirth = localDateDateOfBirth;
        this.genderOfThePassenger = genderOfThePassenger;
    }


}


