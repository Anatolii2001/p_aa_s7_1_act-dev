package app.services;

import app.entities.CategoryType;
import app.entities.Flight;
import app.entities.Seat;
import app.repositories.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final FlightService flightService;

    @Autowired
    public SeatServiceImpl(SeatRepository seatRepository, FlightService flightService) {
        this.seatRepository = seatRepository;
        this.flightService = flightService;
    }

    @Override
    public Optional<Seat> findById(Long id) {
        return seatRepository.findById(id);
    }

    @Override
    public Set<Seat> findAll() {
        return new HashSet<>(seatRepository.findAll());
    }

    @Override
    public Seat save(Seat seat) {
        if (seat == null) throw new IllegalArgumentException("Seat must not be null");
        if (seat.getId() != 0) throw new IllegalArgumentException("Unable to save: id must be = 0.");
        return seatRepository.save(seat);
    }

    @Override
    public Seat update(Seat seat) {
        if (seat == null) throw new IllegalArgumentException("Seat must not be null");
        if (seat.getId() == 0) throw new IllegalArgumentException("Unable update seat with id = 0");
        Optional<Seat> seatToUpdate = seatRepository.findById(seat.getId());
        if (seatToUpdate.isEmpty()) throw new IllegalArgumentException("Seat does not exist");
        seat.setAircraft(seatToUpdate.get().getAircraft());
        return seatRepository.save(seat);
    }

    @Override
    public boolean deleteById(Long id) {
        if (seatRepository.existsById(id)) {
            seatRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void deleteAll() {
        seatRepository.deleteAll();
    }


    @Override
    public Set<Seat> findAllByFlightId(Long id) {
        Flight flight = flightService.findFlightById(id);
        if (flight == null) throw new IllegalArgumentException("Not found flight with id = " + id);
        return flightService.findFlightById(id).getAircraft().getSeats();
    }

    @Override
    public Set<Seat> findAllByFlightIdAndCategory(Long id, CategoryType categoryType) {
        return findAllByFlightId(id)
                .stream()
                .filter(seat -> seat.getCategoryType().equals(categoryType))
                .collect(Collectors.toSet());
    }

    @Override
    public int getSoldNumberByFlightId(Long id) {
        return (int) findAllByFlightId(id)
                .stream()
                .filter(Seat::isSold)
                .count();
    }

    @Override
    public int getUnsoldNumberByFlightId(Long id) {
        return (int) findAllByFlightId(id)
                .stream()
                .filter(seat -> !seat.isSold())
                .count();
    }

    @Override
    public int getRegisteredNumberByFlightId(Long id) {
        return (int) findAllByFlightId(id)
                .stream()
                .filter(Seat::isRegistered)
                .count();
    }

    @Override
    public Optional<Seat> findByFlightIdAndSeatNumber(Long id, String seatNumber) {
        return findAllByFlightId(id).stream()
                .filter(seat -> seat.getSeatNumber().equals(seatNumber))
                .findFirst();
    }
}
