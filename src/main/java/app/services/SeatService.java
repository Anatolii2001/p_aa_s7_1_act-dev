package app.services;

import app.entities.CategoryType;
import app.entities.Flight;
import app.entities.Seat;

import java.util.Optional;
import java.util.Set;

/**
 * The service is allowing all operations with {@link Seat} including
 * CRUD, find by {@link Flight} and {@link CategoryType}, get number of
 * sold, unsold and registered seats.
 *
 * @author Shchepin Maksim
 */
public interface SeatService {

    /**
     * Find a {@link Seat} among all previously saved seats from all registered flights
     * by their unique <code>id</code> and return <code>Optional</code> result.
     *
     * @param id seat id to be found.
     * @return an {@link Optional} with the find results.
     */
    Optional<Seat> findById(Long id);

    /**
     * Find all seats among all previously saved seats from all registered flights
     * and return <code>List</code> of find results.
     *
     * @return a {@link Set} with the find results.
     */
    Set<Seat> findAll();

    /**
     * Save a {@link Seat} into repository. Use the returned instance for further operations as the save operation
     * at least will change the seat id.
     *
     * @param seat to be saved.
     * @return a consistent state {@link Seat}.
     * @throws IllegalArgumentException if seat is {@code null} or {@code seat_id != 0}.
     */
    Seat save(Seat seat);

    /**
     * Update an existing {@link Seat}. Use the returned instance for further operations.
     *
     * @param seat to be updated.
     * @return the saved seat.
     * @throws IllegalArgumentException if seat is {@code null}, {@code seat_id = 0} or seat does not exist.
     */
    Seat update(Seat seat);

    /**
     * Delete an existing {@link Seat}: at first finds for a seat by <code>id</code> and if successful deletes it.
     * Returns <code>true</code> if a seat found and <code>false</code> otherwise.
     *
     * @param id seat id to be deleted.
     * @return the updated seat.
     */
    boolean deleteById(Long id);

    /**
     * Clear seats' repository.
     *
     */
    void deleteAll();

    /**
     * Find for all seats of a {@link Flight} with the specified <code>id</code>.
     *
     * @param id flight id to be used for the search.
     * @return the list of seats for the specified flight.
     * @throws IllegalArgumentException if flight does not exist.
     */
    Set<Seat> findAllByFlightId(Long id);

    /**
     * Find for all seats of a {@link Flight} with the specified <code>id</code>
     * and {@link CategoryType}.
     *
     * @param id the flight id to be used for the search.
     * @param categoryType a type of seat to be used for the search("BUSINESS", "ECONOMY", etc).
     * @return the set of seats for the specified flight and category.
     * @throws IllegalArgumentException if flight does not exist.
     */
    Set<Seat> findAllByFlightIdAndCategory(Long id, CategoryType categoryType);

    /**
     * Return the number of sold seats for the specified flight by <code>id</code>.
     *
     * @param id the flight id to be used for the search.
     * @return the number of sold seats for the specified {@link Flight}.
     * @throws IllegalArgumentException if flight does not exist.
     */
    int getSoldNumberByFlightId(Long id);

    /**
     * Return the number of unsold seats for the specified flight by <code>id</code>.
     *
     * @param id the flight id to be used for the search.
     * @return the number of unsold seats for the specified {@link Flight}.
     * @throws IllegalArgumentException if flight does not exist.
     */
    int getUnsoldNumberByFlightId(Long id);

    /**
     * Return the number of registered seats for the specified flight by <code>id</code>.
     *
     * @param id the flight id to be used for the search.
     * @return the number of registered seats for the specified {@link Flight}.
     * @throws IllegalArgumentException if flight does not exist.
     */
    int getRegisteredNumberByFlightId(Long id);

    /**
     * Find a seat by seat's number ("18A" or "5F") in certain flight found by flight's {@code id}.
     * @param id a flight's id to be used for the search.
     * @param seatNumber a seat's number to be used for the search.
     * @return {@code Optional} with found {@link Seat} or {@code null}
     * @throws IllegalArgumentException if flight does not exist.
     */
    Optional<Seat> findByFlightIdAndSeatNumber(Long id, String seatNumber);
}
