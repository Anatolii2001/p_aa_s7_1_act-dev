package app.services;

import app.entities.Passenger;
import app.repositories.PassengerRepository;
import app.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Class PassengerServiceImpl with CRUD methods for Passenger
 *
 * @author Tamara Ustyan
 */
@Service
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();}




    public PassengerServiceImpl(PassengerRepository passengerRepository, RoleService roleService, UserRepository userRepository) {
        this.passengerRepository = passengerRepository;
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    // достать всх пассажиров
    @Override
    public List<Passenger> getAllPassenger() {
        return passengerRepository.findAll();
    }
    // сохранить пассажира
    @Override
    public void savePassenger(Passenger passenger) {
        if (passenger.getRoles().isEmpty()) {
            passenger.addRoleToCollection(roleService.findRoleByName("ROLE_PASSENGER"));
        }
        passenger.setPassword(passwordEncoder.encode(passenger.getPassword()));
        passengerRepository.save(passenger);


    }
    // достать пассажира
    @Override
    public Passenger getPassenger(long id) {
        Passenger passenger = null;
        Optional<Passenger> optional = passengerRepository.findById(id);
        if (optional.isPresent()) {
            passenger = optional.get();
        }
        return passenger;
    }
    // удалить пассажира
    @Override
    public void deletePassenger(long id) {
        passengerRepository.deleteById(id);
    }
    // удалить всех пассажиров
    @Override
    public void deleteAll() {
        passengerRepository.deleteAll();
    }
// обновить пассажира, метод на основе добавления
    @Override
    public Passenger update(Passenger passenger) {
        passenger.addRoleToCollection(roleService.findRoleByName("ROLE_PASSENGER"));
        passenger.setPassword(passwordEncoder.encode(passenger.getPassword()));
        return passengerRepository.save(passenger);

    }

//    public UserRepository getUserRepository() {
//        return userRepository;
//    }

//    public BCryptPasswordEncoder getPasswordEncoder() {
//        return passwordEncoder;
//    }
}

