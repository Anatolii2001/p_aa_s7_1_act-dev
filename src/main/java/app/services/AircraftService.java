package app.services;

import app.entities.Aircraft;

import java.util.List;
import java.util.Optional;

/**
 * The service is allowing CRUD operations with {@link Aircraft} as well as find by board number
 * and flexibly search by other parameters.
 *
 * @author Shchepin Maksim
 */
public interface AircraftService {

    /**
     * Find an {@link Aircraft} by their unique <code>id</code> and return <code>Optional</code> result.
     *
     * @param id aircraft id to be found.
     * @return an {@link Optional} with the find results.
     */
    Optional<Aircraft> findById(Long id);

    /**
     * Find an {@link Aircraft} by their unique <code>boardNumber</code> and return <code>Optional</code> result.
     *
     * @param boardNumber aircraft's board number to be found.
     * @return an {@link Optional} with the find results.
     */
    Optional<Aircraft> findByBoardNumber(String boardNumber);

    /**
     * Find all aircrafts
     * and return <code>List</code> of find results.
     *
     * @return a {@link List} with the find results.
     */
    List<Aircraft> findAll();

    /**
     * Save an {@link Aircraft} into repository. Use the returned instance for further operations
     * because of new aircraft id will be assigned.
     *
     *
     * @param aircraft to be saved, must be {@code NotNull} as well as their id {@code = 0}.
     * @return saved instance for further operations.
     * @throws IllegalArgumentException if aircraft is {@code null} or their id {@code != 0}.
     */
    Aircraft save(Aircraft aircraft);

    /**
     * Update an existing {@link Aircraft}. Use the returned instance for further operations.
     *
     * @param aircraft to be updated, must be {@code NotNull} as well as exists by their {@code id}.
     * @return updated instance for further operations.
     * @throws IllegalArgumentException if aircraft is {@code null} or does not found by their {@code id}.
     */
    Aircraft update(Aircraft aircraft);

    /**
     * Delete an existing {@link Aircraft}: at first finds for an aircraft by <code>id</code>
     * and if successful, deletes it.
     *
     * @param id aircraft id to be deleted.
     * @return <code>true</code> if the existed aircraft deleted, <code>false</code> if not found.
     */
    boolean deleteById(Long id);

    /**
     * Clear aircraft's repository.
     *
     */
    void deleteAll();

    /**
     * The method that allows to find aircrafts by <b>intersection</b> of specified parameters.
     * All parameters are nullable and by this feature flexible find is available.<br><br>
     *
     * For example:<br>
     * You need to find all aircrafts by model & brand, then you just should
     * to call the method with these parameters and pass <code>null</code> to the rest as follows:<br><br>
     *
     * {@code List<Aircraft> flies = getByParams("Airbus", "A320", null, null);}<br><br>
     *
     * The result of the above call will be all Airbus A320 aircrafts from the repository.
     *
     * @param brand aircraft's manufactured company, for example "Boeing".
     * @param model aircraft's model, for example "767-300ER".
     * @param productionYear aircraft production year, for example "2007".
     * @param flyingRange maximal aircraft's flying distance, for example "11 305" km.
     * @return all aircrafts matches the specified parameters.
     */
    List<Aircraft> searchByParams(String brand, String model, Integer productionYear, Integer flyingRange);

}
