package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Repository
@AllArgsConstructor
@Slf4j
public class HqlAccidentRepository implements AccidentRepository {
    private final CrudRepository crudRepository;

    @Override
    public Accident save(Accident accident) {
        crudRepository.run(session -> session.persist(accident));
        return accident;
    }

    @Override
    public boolean update(Accident accident) {
        try {
            crudRepository.run(session -> session.update(accident));
            return true;
        } catch (Exception e) {
            log.error("Accident with this id is not found", e);
        }
        return false;
    }

    @Override
    public Collection<Accident> findAll() {
        return crudRepository.query("FROM Accident ORDER BY id", Accident.class);
    }

    @Override
    public Optional<Accident> findById(int id) {
        return crudRepository.optional(
                "FROM Accident a LEFT JOIN FETCH a.type LEFT JOIN FETCH a.rules WHERE a.id=:id",
                Accident.class, Map.of("id", id));
    }

    @Override
    public boolean deleteById(int id) {
        Function<Session, Boolean> command = session ->
                session.createQuery("DELETE Accident WHERE id = :id")
                        .setParameter("id", id)
                        .executeUpdate() != 0;
        return crudRepository.tx(command);
    }
}
