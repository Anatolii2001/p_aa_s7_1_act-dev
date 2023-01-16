package app.repositories;

import app.entities.Aircraft;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class AircraftRepositoryCustomImpl implements AircraftRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Aircraft> getIntersection(String brand, String model, Integer productionYear, Integer flyingRange) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Aircraft> query = cb.createQuery(Aircraft.class);
        Root<Aircraft> aircraft = query.from(Aircraft.class);
        List<Predicate> predicates = new ArrayList<>();

        if (brand != null) predicates.add(cb.equal(aircraft.get("brand"), brand));
        if (model != null) predicates.add(cb.equal(aircraft.get("model"), model));
        if (productionYear != null) predicates.add(cb.equal(aircraft.get("productionYear"), productionYear));
        if (flyingRange != null) predicates.add(cb.equal(aircraft.get("flyingRange"), flyingRange));

        query.select(aircraft).where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList();
    }
}
