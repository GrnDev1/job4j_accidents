package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;

@Repository
@AllArgsConstructor
public class HqlAccidentTypeRepository implements AccidentTypeRepository {
    private final CrudRepository crudRepository;

    @Override
    public List<AccidentType> findAll() {
        return crudRepository.query("FROM AccidentType ORDER BY id", AccidentType.class);
    }
}
