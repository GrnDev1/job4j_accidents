package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

@Repository
@AllArgsConstructor
@Slf4j
public class JdbcAccidentRepository implements AccidentRepository {
    private final JdbcTemplate jdbc;
    private final String insertAccidentSql = "INSERT INTO accidents (name, text, address, type_id) VALUES (?, ?, ?, ?)";
    private final String insertParticipatesSql = "INSERT INTO participates_rules (accident_id, rule_id) VALUES (?, ?)";

    @Override
    @Transactional
    public Accident save(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(insertAccidentSql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, accident.getName());
            statement.setString(2, accident.getText());
            statement.setString(3, accident.getAddress());
            statement.setInt(4, accident.getType().getId());
            return statement;
        }, keyHolder);
        accident.setId((int) Objects.requireNonNull(keyHolder.getKeys()).get("id"));

        for (Rule rule : accident.getRules()) {
            jdbc.update(insertParticipatesSql, accident.getId(), rule.getId());
        }
        return accident;
    }

    @Override
    @Transactional
    public boolean update(Accident accident) {
        String updateSql = "UPDATE accidents SET name = ?, text = ?, address = ?, type_id = ? WHERE id = ?";
        boolean result = jdbc.update(updateSql, accident.getName(), accident.getText(),
                accident.getAddress(), accident.getType().getId(), accident.getId()) > 0;
        if (result) {
            String deleteParticipatesSql = "DELETE FROM participates_rules WHERE accident_id = ?";
            jdbc.update(deleteParticipatesSql, accident.getId());
            for (Rule rule : accident.getRules()) {
                jdbc.update(insertParticipatesSql, accident.getId(), rule.getId());
            }
        } else {
            log.error("Accident with this id is not found");
        }
        return result;
    }

    @Override
    public Collection<Accident> findAll() {
        return jdbc.query("SELECT * FROM accidents order by id",
                getAccidentRowMapper());
    }

    @Override
    public Optional<Accident> findById(int id) {
        List<Accident> list = jdbc.query("SELECT * FROM accidents WHERE id = ?",
                getAccidentRowMapper(), id);
        Accident accident = null;
        if (!list.isEmpty()) {
            String sql = "SELECT * FROM rules r WHERE r.id IN (SELECT rule_id FROM participates_rules WHERE accident_id = ?)";
            List<Rule> rules = jdbc.query(sql,
                    (rs, row) -> {
                        Rule rule = new Rule();
                        rule.setId(rs.getInt("id"));
                        rule.setName(rs.getString("name"));
                        return rule;
                    }, id);
            accident = list.get(0);
            accident.setRules(new HashSet<>(rules));
        }
        return list.isEmpty() ? Optional.empty() : Optional.of(accident);
    }

    private static RowMapper<Accident> getAccidentRowMapper() {
        return (rs, row) -> {
            Accident accident = new Accident();
            accident.setId(rs.getInt("id"));
            accident.setName(rs.getString("name"));
            accident.setText(rs.getString("text"));
            accident.setAddress(rs.getString("address"));
            var type = new AccidentType();
            type.setId(rs.getInt("type_id"));
            accident.setType(type);
            return accident;
        };
    }

    @Override
    public boolean deleteById(int id) {
        String deleteAccidentSql = "DELETE FROM accidents WHERE id = ?";
        return jdbc.update(deleteAccidentSql, id) > 0;
    }
}
