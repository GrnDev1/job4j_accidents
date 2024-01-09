package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;

public interface DataAccidentTypeRepository extends CrudRepository<AccidentType, Integer> {
    Collection<AccidentType> findAll();
}