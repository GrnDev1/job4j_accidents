package ru.job4j.accidents.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;

public interface DataAccidentRepository extends CrudRepository<Accident, Integer> {
    Logger LOG = LoggerFactory.getLogger(DataAccidentRepository.class.getName());

    default boolean update(Accident accident) {
        boolean result = true;
        try {
            save(accident);
        } catch (Exception e) {
            result = false;
            LOG.error("Accident with this id is not found", e);
        }
        save(accident);
        return result;
    }

    boolean deleteById(int id);

    Collection<Accident> findAll();
}