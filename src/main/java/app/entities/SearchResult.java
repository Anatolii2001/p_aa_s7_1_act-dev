package app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * SearchResult - is a list of available flights
 * @author Komarov Rostislav
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResult {
    /**
     * list of available routes for desired search
     */
    private List<SearchRoute> routes;
}
