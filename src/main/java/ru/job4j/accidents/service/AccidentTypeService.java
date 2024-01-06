package ru.job4j.accidents.service;

import ru.job4j.accidents.model.AccidentType;

import java.util.List;

public interface AccidentTypeService {
    List<AccidentType> findAll();
}