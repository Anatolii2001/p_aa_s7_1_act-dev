package app;

import app.entities.AirportCode;
import app.entities.AirportName;
import app.entities.Destination;
import app.repositories.DestinationRepository;
import app.services.DestinationService;
import com.neovisionaries.i18n.CountryCode;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Class for unit tests methods
 */


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DestinationApplicationTests {

    @Autowired
    private DestinationService destinationService;
    @Autowired
    private DestinationRepository destinationRepository;

    private List<Destination> destinationsList;

    @BeforeEach
    public void addDestinationToDB() {
        destinationService.deleteAllDestination();
        destinationRepository.deleteAll(destinationsList);
        Destination dest1 = new Destination("city1", "country1", "continent1");
        Destination dest2 = new Destination("city2", "country2", "continent2");
        Destination dest3 = new Destination("city3", "country3", "continent3");
        destinationsList = new ArrayList<>();
        destinationsList.add(dest1);
        destinationsList.add(dest2);
        destinationsList.add(dest3);
        destinationsList.forEach(d -> destinationService.saveDestination(d));
    }

    @AfterEach
    public void deleteDestinationFromDB() {
        destinationService.deleteAllDestination();
        destinationRepository.deleteAll(destinationsList);
    }

    @Test
    public void searchAllDestinations() {
        assertEquals(destinationService.getAllDestinations(), destinationsList);
    }

    @Test
    public void searchByCity() {
        assertEquals(destinationService.getDestinationByCity("city2").getContinent(), "continent2");
    }

    @Test
    public void searchByCountryName() {
        assertEquals(destinationService.getDestinationByCountryName("country3").getContinent(), "continent3");
    }


/** Destinations for tests with right values */

    private Destination rightDestination() {
        return new Destination("Cairo", "Egypt", "Asia");
    }


/** Destinations for tests with wrong values */

    private Destination wrongDestination() {
        return new Destination("Bolgarka", "Krackozhia", "DSSD");
    }


/** Testing method for country code with right Destination's values*/

    @Test
    void getCountry_code() {
        assertEquals(CountryCode.EG, rightDestination().getCountry_code());
    }


/** Testing method for country code with wrong Destination's values*/

    @Test
    void getCountry_codeWithException() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () ->
                wrongDestination().getCountry_code());
        assertEquals("Index 0 out of bounds for length 0", exception.getMessage());
    }


/** Testing method for Airport_name with right Destination's values*/

    @Test
    void getAirport_name() {
        List<AirportName> airportNameList = new ArrayList<>();
        airportNameList.add(AirportName.El_Arish_International_Airport);
        assertEquals(airportNameList, rightDestination().getAirport_name());
    }


/** Testing method for Airport_name with wrong Destination's values*/

    @Test
    void getAirport_nameWithEmptyList() {
        List<AirportName> airportNameList = new ArrayList<>();
        assertEquals(airportNameList, wrongDestination().getAirport_name());
    }


/** Testing method for Airport_code with right Destination's values*/


    @Test
    void getAirport_code() {
        List<AirportCode> airportCodeList = new ArrayList<>();
        airportCodeList.add(AirportCode.AAC);
        assertEquals(airportCodeList, rightDestination().getAirport_code());
    }


/** Testing method for Airport_code with wrong Destination's values*/

    @Test
    void getAirport_code_WithEmptyList() {
        List<AirportCode> airportCodeList = new ArrayList<>();
        assertEquals(airportCodeList, wrongDestination().getAirport_code());
    }

}

