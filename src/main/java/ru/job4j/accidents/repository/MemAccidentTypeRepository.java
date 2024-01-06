package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemAccidentTypeRepository implements AccidentTypeRepository {
    private final List<AccidentType> types = new ArrayList<>();

    public MemAccidentTypeRepository() {
        types.add(new AccidentType(1, "Two cars"));
        types.add(new AccidentType(2, "Car and person"));
        types.add(new AccidentType(3, "Car and bike"));
    }

    @Override
    public List<AccidentType> findAll() {
        return types;
    }
}
