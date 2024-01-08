package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class MemAccidentTypeRepository implements AccidentTypeRepository {
    private final Map<Integer, AccidentType> types = new HashMap<>();

    public MemAccidentTypeRepository() {
        types.put(1, new AccidentType(1, "Two cars"));
        types.put(2, new AccidentType(2, "Car and person"));
        types.put(3, new AccidentType(3, "Car and bike"));
    }

    @Override
    public Collection<AccidentType> findAll() {
        return types.values();
    }
}
