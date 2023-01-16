package app.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;


/**
 * Aircraft's seat for passengers with next fields:
 *
 *<ul>
 *<li> <b>seatNumber</b> - the seat's number, for example "12B"</li>
 *<li> <b>fare</b> - basic fare per seat, no extra charge</li>
 *<li> <b>isRegistered</b> - is passenger registered or not for this seat</li>
 *<li> <b>isSold</b> - is this seat sold or not</li>
 *<li> <b>category</b> - category of the seat - "Business" or "Economy"</li>
 *<li> <b>aircraft</b> - aircraft of the seat</li>
 *</ul>
 *
 * @author Shchepin Maksim
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seat_number")
    private String seatNumber;
    @Column
    private Integer fare;
    @Column(name = "is_registered")
    private boolean isRegistered;
    @Column(name = "is_sold")
    private boolean isSold;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "category")
    private CategoryType categoryType;
    @ManyToOne
    @JoinColumn(name = "aircraft_id", nullable = false)
    @JsonBackReference
    private Aircraft aircraft;

    @Override
    public String toString() {
        return new StringBuilder("Seat{")
                .append("id=").append(id)
                .append(", seatNumber='").append(seatNumber).append('\'')
                .append(", fare=").append(fare)
                .append(", isRegistered=").append(isRegistered)
                .append(", isSold=").append(isSold)
                .append(", aircraft=").append(aircraft)
                .append('}').toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Seat seat = (Seat) o;
        return Objects.equals(id, seat.id) && Objects.equals(getSeatNumber(), seat.getSeatNumber());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
