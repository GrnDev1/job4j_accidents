package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Authority;

public interface AuthorityService {
    Authority findByAuthority(String authority);
}