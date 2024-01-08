package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {
    private final AccidentRepository jdbcAccidentRepository;

    @Override
    public Accident save(Accident accident) {
        return jdbcAccidentRepository.save(accident);
    }

    @Override
    public boolean update(Accident accident) {
        return jdbcAccidentRepository.update(accident);
    }

    @Override
    public Collection<Accident> findAll() {
        return jdbcAccidentRepository.findAll();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return jdbcAccidentRepository.findById(id);
    }

    @Override
    public boolean deleteById(int id) {
        return jdbcAccidentRepository.deleteById(id);
    }
}
