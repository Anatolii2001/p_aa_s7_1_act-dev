package app.services;

import app.entities.Passport;

import java.util.List;
import java.util.Optional;

public interface PassportService {

    List<Passport> findAllPassports();

    Optional<Passport> getOnePassport(Long id);

    void insertPassport(Passport passport);

    void updatePassport(Passport passport);

    void deletePassport(Long id);
}