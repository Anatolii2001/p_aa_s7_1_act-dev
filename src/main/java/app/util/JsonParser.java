/*
package app.util;

import app.entities.Destination;
import app.entities.Route;
import app.entities.Search;
import app.services.DestinationService;

import app.services.SearchService;
import lombok.RequiredArgsConstructor;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JsonParser {

    private final DestinationService destinationService;
    private final SearchService searchService;


    public Search getSearchByJson(String response) throws ParseException {

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response);
        Destination destinationFrom =
                destinationService.getDestinationByCity(jsonObject.get("from").toString()).isPresent()
                        ? destinationService.getDestinationByCity(jsonObject.get("from").toString()).get()
                        : null;
        System.out.println("JP Destination From: " + destinationFrom);
        Destination destinationTo = destinationService.getDestinationByCity(jsonObject.get("to").toString()).isPresent()
                ? destinationService.getDestinationByCity(jsonObject.get("to").toString()).get()
                : null;
       System.out.println("JP Destination To: " + destinationTo);
        LocalDate departureDate;
        try {
            departureDate = LocalDate.parse(jsonObject.get("when").toString());
        } catch (Exception e) {
            departureDate = null;
        }
       System.out.println("JP DepDate: " + departureDate);

        if (destinationFrom != null && destinationTo != null & departureDate != null) {
            Route route = new Route();
            route.setFrom(destinationFrom);
            route.setTo(destinationTo);
            route.setDepartureDate(departureDate);
            route.setCategory(Category.ECONOMY);
            System.out.println("JP Route: " + route);
            List<Route> listRouts = new ArrayList<>();
            listRouts.add(route);
            System.out.println("JP List Routes: " + listRouts);
            Search search = new Search();
            search.setRoutes(listRouts);
            System.out.println("JP search: " + search);

            return search;
        } else {
            return null;
        }
    }
}
*/
