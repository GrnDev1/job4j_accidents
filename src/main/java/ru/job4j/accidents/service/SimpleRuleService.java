package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.DataRuleRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class SimpleRuleService implements RuleService {
    private final DataRuleRepository dataRuleRepository;

    @Override
    public Collection<Rule> findAll() {
        return dataRuleRepository.findAll();
    }

    @Override
    public Set<Rule> findAllById(List<Integer> list) {
        return dataRuleRepository.findAllById(list);
    }
}
