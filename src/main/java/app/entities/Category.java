package app.entities;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Entity Category with next fields:
 *
 *<ul>
 *<li> <b>categoryType</b> - type of category; field for saving type of category in DB</li>
 *</ul>
 *
 * @author Shonoeva Darya
 */


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class Category {
    @Id
    @Column
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;
}
