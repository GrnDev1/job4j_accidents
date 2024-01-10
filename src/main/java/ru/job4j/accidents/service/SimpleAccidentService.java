package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.DataAccidentRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class SimpleAccidentService implements AccidentService {
    private final DataAccidentRepository dataAccidentRepository;

    @Override
    public Accident save(Accident accident) {
        return dataAccidentRepository.save(accident);
    }

    @Override
    public boolean update(Accident accident) {
        Optional<Accident> accidentOptional = dataAccidentRepository.findById(accident.getId());
        if (accidentOptional.isEmpty()) {
            log.error("Accident with this id is not found");
            return false;
        }
        dataAccidentRepository.save(accident);
        return true;
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
