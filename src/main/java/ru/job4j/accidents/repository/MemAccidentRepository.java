package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MemAccidentRepository implements AccidentRepository {
    private final AtomicInteger id = new AtomicInteger();
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    public MemAccidentRepository() {
        Rule rule = new Rule(1, "Rule 1");
        this.save(new Accident(0, "Accident1", "Text1", "Address1", new AccidentType(1, "Two cars"), Set.of(rule)));
        this.save(new Accident(0, "Accident2", "Text2", "Address2", new AccidentType(2, "Car and person"), Set.of(rule)));
        this.save(new Accident(0, "Accident3", "Text3", "Address3", new AccidentType(3, "Car and bike"), Set.of(rule)));
        this.save(new Accident(0, "Accident4", "Text4", "Address4", new AccidentType(2, "Car and person"), Set.of(rule)));
    }

    @Override
    public Accident save(Accident accident) {
        accident.setId(id.getAndIncrement());
        accidents.put(accident.getId(), accident);
        return accident;
    }

    @Override
    public boolean update(Accident accident) {
        return accidents.computeIfPresent(accident.getId(), (id, value) -> {
            value.setName(accident.getName());
            value.setText(accident.getText());
            value.setAddress(accident.getAddress());
            value.setType(accident.getType());
            value.setRules(accident.getRules());
            return value;
        }) != null;
    }

    @Override
    public Collection<Accident> findAll() {
        return accidents.values();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    @Override
    public boolean deleteById(int id) {
        return accidents.remove(id) != null;
    }
}
