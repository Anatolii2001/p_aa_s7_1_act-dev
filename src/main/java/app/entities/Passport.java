package app.entities;

import lombok.*;

import javax.persistence.*;

/**
 * Class Passport with properties <b>number</b>,
 * <b>expiryDate</b>, <b>nationality</b>
 *
 *
 * Паспорт класса со свойствами // какие могут быть у паспорта ?
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
@Table(name = "PassportUsers")
public class Passport {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column(name = "type")
    private String type; // тип

    @NonNull
    @Column(name = "codeOfIssuingState")
    private String codeOfIssuingState; // код государства выдачи

    @NonNull
    @Column(name = "passportNo")
    private int passportNo; // № паспорта

    @NonNull
    @Column(name = "surname")
    private String surname; // фамилия

    @NonNull
    @Column(name = "givenNames")
    private String givenNames; // имя, отчество

    @NonNull
    @Column(name = "nationality")
    private String nationality; // гражданство

    @NonNull
    @Column(name = "personalNo")
    private int personalNo; // личный код

    @NonNull
    @Column(name = "dateOfBirth")
    private String dateOfBirth; // дата рождения

    @NonNull
    @Column(name = "placeOfBirth")
    private String placeOfBirth; // место рождения

    @NonNull
    @Column(name = "sex")
    private String sex; // пол

    @NonNull
    @Column(name = "dateOfIssue")
    private String dateOfIssue; // дата выдачи

    @NonNull
    @Column(name = "dateOfExpiry")
    private String dateOfExpiry; // дата окончания срока действия

    @NonNull
    @Column(name = "authority")
    private String authority; // орган выдавший документ
}