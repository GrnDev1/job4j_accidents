package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;

public interface DataAccidentRepository extends CrudRepository<Accident, Integer> {

    boolean deleteById(int id);

    Collection<Accident> findAll();
}