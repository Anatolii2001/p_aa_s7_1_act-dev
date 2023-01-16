package app.services;

import app.entities.Passport;
import app.repositories.PassportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PassportServiceImpl implements PassportService {
    private final PassportRepository passportRepository;

    @Autowired
    public PassportServiceImpl(PassportRepository passportRepository) {
        this.passportRepository = passportRepository;
    }

    @Override
    public List<Passport> findAllPassports() {
        return passportRepository.findAll();
    }

    @Override
    public Optional<Passport> getOnePassport(Long id) {
        return Optional.ofNullable(passportRepository.findPassportById(id));
    }

    @Override
    public void insertPassport(Passport passport) {
        passportRepository.save(passport);
    }

    @Override
    public void updatePassport(Passport passport) {
        passportRepository.save(passport);
    }

    @Override
    public void deletePassport(Long id) {
        passportRepository.deleteById(id);
    }
}
