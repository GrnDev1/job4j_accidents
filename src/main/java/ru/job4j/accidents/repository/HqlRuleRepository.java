package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;

@Repository
@AllArgsConstructor
public class HqlRuleRepository implements RuleRepository {
    private final CrudRepository crudRepository;

    @Override
    public Collection<Rule> findAll() {
        return crudRepository.query("FROM Rule ORDER BY id", Rule.class);
    }

    @Override
    public Set<Rule> findAllById(List<Integer> list) {
        List<Rule> rules = crudRepository.query("FROM Rule WHERE id IN :list ORDER BY id",
                Rule.class, Map.of("list", list));
        return new HashSet<>(rules);
    }
}
