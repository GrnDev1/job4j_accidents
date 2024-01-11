package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.DataUserRepository;

import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class SimpleUserService implements UserService {
    private final DataUserRepository repository;
    private final PasswordEncoder encoder;
    private final AuthorityService authorityService;

    @Override
    public Optional<User> save(User user) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorityService.findByAuthority("ROLE_USER"));
        Optional<User> userOptional = repository.findByUsername(user.getUsername());
        if (userOptional.isPresent()) {
            log.error("User with this mail already exists");
            return Optional.empty();
        }
        return Optional.of(repository.save(user));
    }
}
