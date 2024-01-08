package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;

public interface AccidentTypeRepository {

    Collection<AccidentType> findAll();
}
