package app.services;

import app.entities.Aircraft;
import app.repositories.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;

    @Autowired
    public AircraftServiceImpl(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    @Override
    public Optional<Aircraft> findById(Long id) {
        return aircraftRepository.findById(id);
    }

    @Override
    public List<Aircraft> findAll() {
        return aircraftRepository.findAll();
    }


    @Override
    public Aircraft save(Aircraft aircraft) {
        if (aircraft == null || aircraft.getId() == null || aircraft.getId() != 0) {
            throw new IllegalArgumentException("Incorrect data: only nonNull aircraft with id=0" +
                    " can be saved instead of " + aircraft);
        }
        return aircraftRepository.save(aircraft);
    }

    @Override
    public Aircraft update(Aircraft aircraft) {
        if (aircraft == null) throw new IllegalArgumentException("Incorrect data: aircraft is null");
        if (aircraft.getId() == null) throw new IllegalArgumentException("Incorrect data: aircraft's id is null");
        if (!aircraftRepository.existsById(aircraft.getId())) throw new IllegalArgumentException("Incorrect data:" +
                " only existing aircraft can be updated instead of " + aircraft);

        return aircraftRepository.save(aircraft);
    }

    @Override
    public boolean deleteById(Long id) {
        if (aircraftRepository.existsById(id)) {
            aircraftRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void deleteAll() {
        aircraftRepository.deleteAll();
    }

    @Override
    public List<Aircraft> searchByParams(String brand, String model, Integer productionYear, Integer flyingRange) {
        return aircraftRepository.getIntersection(brand, model, productionYear, flyingRange);
    }

    @Override
    public Optional<Aircraft> findByBoardNumber(String boardNumber) {
        return aircraftRepository.findByBoardNumber(boardNumber);
    }
}
