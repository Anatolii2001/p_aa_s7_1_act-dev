package app.services;

import app.entities.Destination;

import java.util.List;


public interface DestinationService {

    List<Destination> getAllDestinations();

    Destination saveDestination (Destination destination);

    void updateDestination (Destination destination);

    void deleteAllDestination();

    Destination getDestinationById(Long id);

    Destination getDestinationByCity (String city);

    Destination getDestinationByCountryName (String countryName);

}
