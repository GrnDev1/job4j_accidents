package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface RuleRepository {
    Collection<Rule> findAll();

    Set<Rule> findAllById(List<Integer> list);
}
