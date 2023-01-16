package app.repositories;

import app.entities.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {

//@Query("Select d from Destination d where d.id = :id")
Destination findDestinationById(Long id);


//@Query("select d from Destination d where d.city = :city")
Destination findDestinationByCity(String city);


//@Query("select d from Destination d where d.country_name = :country_name")
Destination findDestinationByCountryName(String countryName);

}
