package app.services;

import app.entities.Destination;
import app.repositories.DestinationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationServiceImpl implements DestinationService {

private final DestinationRepository destinationRepository;

    public DestinationServiceImpl(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }


    @Override
    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    @Override
    public Destination saveDestination(Destination destination) {
        destinationRepository.save(destination);
        return destination;
    }

    @Override
    public void updateDestination(Destination destination) {
        destinationRepository.findDestinationById(destination.getId());
        destinationRepository.save(destination);

    }

    @Override
    public void deleteAllDestination() {
        destinationRepository.deleteAll();

    }

    @Override
    public Destination getDestinationById(Long id) {
        return destinationRepository.findDestinationById(id);
    }

    @Override
    public Destination getDestinationByCity(String city) {
        return destinationRepository.findDestinationByCity(city);
    }

    @Override
    public Destination getDestinationByCountryName(String countryName) {
        return destinationRepository.findDestinationByCountryName(countryName);
    }
}
