package app.services;

import app.entities.Passenger;

import java.util.List;

public interface PassengerService {

    List<Passenger> getAllPassenger();

    void savePassenger(Passenger passenger);

    Passenger getPassenger(long id);

    void deletePassenger(long id);

    public void deleteAll();

    Passenger update(Passenger passenger);

}
