package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.DataAccidentRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {
    private final DataAccidentRepository dataAccidentRepository;

    @Override
    public Accident save(Accident accident) {
        return dataAccidentRepository.save(accident);
    }

    @Override
    public boolean update(Accident accident) {
        return dataAccidentRepository.update(accident);
    }

    @Override
    public Collection<Accident> findAll() {
        return dataAccidentRepository.findAll();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return dataAccidentRepository.findById(id);
    }

    @Override
    public boolean deleteById(int id) {
        return dataAccidentRepository.deleteById(id);
    }
}
