package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.DataAccidentTypeRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class SimpleAccidentTypeService implements AccidentTypeService {
    private final DataAccidentTypeRepository dataAccidentTypeRepository;

    @Override
    public Collection<AccidentType> findAll() {
        return dataAccidentTypeRepository.findAll();
    }
}