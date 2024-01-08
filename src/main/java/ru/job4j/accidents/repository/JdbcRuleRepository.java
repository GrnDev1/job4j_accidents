package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;

@Repository
@AllArgsConstructor
public class JdbcRuleRepository implements RuleRepository {

    private final JdbcTemplate jdbc;

    @Override
    public Collection<Rule> findAll() {
        return jdbc.query("SELECT * FROM rules order by id",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
    }

    @Override
    public Set<Rule> findAllById(List<Integer> list) {
        StringJoiner joiner = new StringJoiner(", ", "(", ")");
        for (Integer i : list) {
            joiner.add(i.toString());
        }
        List<Rule> result = jdbc.query(
                String.format("SELECT * FROM rules WHERE id IN %s", joiner),
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
        return new HashSet<>(result);
    }

}
