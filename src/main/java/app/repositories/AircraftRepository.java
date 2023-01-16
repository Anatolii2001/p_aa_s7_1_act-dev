package app.repositories;


import app.entities.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long>, AircraftRepositoryCustom {

    @Query("SELECT a FROM Aircraft a WHERE a.boardNumber = :number")
    Optional<Aircraft> findByBoardNumber(@Param("number") String boardNumber);



}
