package app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * Class Search, describes search request
 * @author Komarov Rostislav
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "searches")
@NamedEntityGraph(name = "graph.Search.routes", attributeNodes = @NamedAttributeNode("routes"))
public class Search {

    /**
     * id of element. Is primary key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * list with routes specified in search form
     */

    // TODO: merge PAS1-5 Rote for Route entity
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Route> routes;

    /**
     * amount of passengers
     * minimal value is 1, maximum value is 50,
     * cant be null
     */

    @Column
    @NonNull
    @Min(value = 1)
    @Max(value = 50)
    private Integer passengersAmount;

    /**
     * specified category of flight {@link app.entities.CategoryType}
     * cant be null
     */

    @Enumerated(value = EnumType.STRING)
    @NonNull
    private CategoryType categoryType;

}
