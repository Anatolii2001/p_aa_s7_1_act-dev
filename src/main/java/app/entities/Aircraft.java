package app.entities;

import app.util.AircraftBuilder;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 * Aircraft with next fields:
 * <ul>
 * <li> <b>boardNumber</b> - the unique board's number, for example "RA-12981"</li>
 * <li> <b>brand</b> - aircraft's manufactured company, for example "Boeing"</li>
 * <li> <b>model</b> - aircraft's model, for example "767-300ER"</li>
 * <li> <b>productionYear</b> - aircraft production year, for example "2007"</li>
 * <li> <b>flyingRange</b> - maximal aircraft's flying distance, for example "11 305" km</li>
 * <li> <b>seats</b> - the set of seats for passengers</li>
 * </ul>
 *
 * There is the {@link AircraftBuilder} for simply creation of an aircraft, for example:<br><br>
 *
 * {@code Aircraft fly = AircraftBuilder}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .newBuilder()}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .id(0L)}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .number("RA-12345")}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .brand("Airbus")}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .model("A310")}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .year(2008)}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .range(5600)}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .interior(categoryService.getByName("BUSINESS"))}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .headRows(2, "ABCD")}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .nextRow("3ABCDE")}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .fare(500)}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .and()}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .interior(categoryService.getByName("ECONOMY"))}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .rows(4, 6, "ABCDEF")}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .fare(150)}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .and()}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .build();}<br><br>
 *
 * @author Shchepin Maksim
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "aircrafts")
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "board_number")
    private String boardNumber;

    @Column
    private String brand;

    @Column
    private String model;

    @Column(name = "production_year")
    private Integer productionYear;

    @Column(name = "flying_range")
    private Integer flyingRange;

    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Seat> seats;

    @Override
    public String toString() {
        return new StringBuilder("Aircraft{")
        .append("id=").append(id)
        .append(", boardNumber='").append(boardNumber).append('\'')
        .append(", brand='").append(brand).append('\'')
        .append(", model='").append(model).append('\'')
        .append(", productionYear=").append(productionYear)
        .append(", flyingRange=").append(flyingRange)
        .append('}').toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Aircraft aircraft = (Aircraft) o;
        return getId().equals(aircraft.getId()) && getBoardNumber().equals(aircraft.getBoardNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBoardNumber());
    }
}