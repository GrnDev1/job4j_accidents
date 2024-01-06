package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;

@Repository
public class MemRuleRepository implements RuleRepository {
    private final Map<Integer, Rule> rules = new HashMap<>();

    public MemRuleRepository() {
        rules.put(1, new Rule(1, "Rule 1"));
        rules.put(2, new Rule(2, "Rule 2"));
        rules.put(3, new Rule(3, "Rule 3"));
    }

    @Override
    public Collection<Rule> findAll() {
        return rules.values();
    }

    @Override
    public Set<Rule> findAllById(List<Integer> list) {
        Set<Rule> result = new HashSet<>();
        for (Integer i : list) {
            result.add(rules.get(i));
        }
        return result;
    }
}
