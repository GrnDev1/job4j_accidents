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
    private final AccidentRepository hqlAccidentRepository;

    @Override
    public Accident save(Accident accident) {
        return hqlAccidentRepository.save(accident);
    }

    @Override
    public boolean update(Accident accident) {
        return hqlAccidentRepository.update(accident);
    }

    @Override
    public Collection<Accident> findAll() {
        return hqlAccidentRepository.findAll();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return hqlAccidentRepository.findById(id);
    }

    @Override
    public boolean deleteById(int id) {
        return hqlAccidentRepository.deleteById(id);
    }
}
