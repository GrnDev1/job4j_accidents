package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.User;

public interface DataUserRepository extends CrudRepository<User, Integer> {
}