package app.repositories;

import app.entities.Aircraft;

import java.util.List;

public interface AircraftRepositoryCustom {

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
    List<Aircraft> getIntersection(String brand, String model, Integer productionYear, Integer flyingRange);
}
