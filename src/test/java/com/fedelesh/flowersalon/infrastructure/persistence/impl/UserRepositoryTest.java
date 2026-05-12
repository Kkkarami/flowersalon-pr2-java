package com.fedelesh.flowersalon.infrastructure.persistence.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fedelesh.flowersalon.domain.entity.User;
import com.fedelesh.flowersalon.domain.enums.Role;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class UserRepositoryTest {

    private final UserRepositoryImpl repository = new UserRepositoryImpl();

    @Test
    void shouldSaveAndFindById() {

        User user = new User(
                UUID.randomUUID(),
                "Test",
                "User",
                "test@mail.com",
                "+380000000000",
                Role.CLIENT,
                "hash",
                LocalDateTime.now());

        repository.save(user);

        Optional<User> result = repository.findById(user.getUserId());

        assertTrue(result.isPresent());
    }

    @Test
    void shouldFindByEmail() {

        String email = "mail@mail.com";

        User user = new User(
                UUID.randomUUID(), "Mail", "User", email, "+380000000000", Role.CLIENT, "hash", LocalDateTime.now());

        repository.save(user);

        Optional<User> result = repository.findByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
    }

    @Test
    void shouldUpdateUser() {

        User user = new User(
                UUID.randomUUID(),
                "Old",
                "Name",
                "update@mail.com",
                "+380000000000",
                Role.CLIENT,
                "hash",
                LocalDateTime.now());

        repository.save(user);

        user.setFirstName("New");

        repository.update(user);

        User updated = repository.findById(user.getUserId()).orElseThrow();

        assertEquals("New", updated.getFirstName());
    }

    @Test
    void shouldDeleteUser() {

        User user = new User(
                UUID.randomUUID(),
                "Delete",
                "User",
                "delete@mail.com",
                "+380000000000",
                Role.CLIENT,
                "hash",
                LocalDateTime.now());

        repository.save(user);

        repository.deleteById(user.getUserId());

        Optional<User> result = repository.findById(user.getUserId());

        assertTrue(result.isEmpty());
    }
}
