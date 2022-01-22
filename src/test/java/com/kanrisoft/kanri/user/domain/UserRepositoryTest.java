package com.kanrisoft.kanri.user.domain;

import com.kanrisoft.kanri.user.DbConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest extends DbConfig {

    @Autowired
    private UserRepository repository;

    @Test
    void shouldSaveAnEntity() {
        var email = Email.of("test@test.com");
        var entity = UserEntity.of("first", "last", email, "password", null);

        var savedOne = repository.save(entity);

        assertAll(
                () -> assertNotNull(savedOne.getId()),
                () -> assertEquals(savedOne.getEmail(), entity.getEmail()),
                () -> assertEquals(savedOne.getFirstName(), entity.getFirstName()),
                () -> assertEquals(savedOne.getLastName(), entity.getLastName()),
                () -> assertEquals(savedOne.getPassword(), entity.getPassword()),
                () -> assertEquals(savedOne.getPhone(), entity.getPhone()),
                () -> assertEquals(savedOne.getRoles(), entity.getRoles()),
                () -> assertEquals(savedOne.getStatus(), entity.getStatus())
        );
    }

    @Test
    void shouldBeAbleToRetrieveAnEntity() {
        var email = Email.of("test@test.com");
        var entity = UserEntity.of("first", "last", email, "password", null);
        var savedOne = repository.save(entity);

        var searchedOne = repository.findByEmail(email);

        assertEquals(savedOne, searchedOne.get());
    }

    @Test
    void findByEmail() {
        var email = Email.of("test@test.com");
        var entity = UserEntity.of("first", "last", email, "password", null);

        var beforeSave = repository.findByEmail(email);
        assertTrue(beforeSave.isEmpty());

        repository.save(entity);

        var afterSave = repository.findByEmail(email);
        assertTrue(afterSave.isPresent());
    }

    @Test
    void existsByEmail() {
        var email = Email.of("test@test.com");
        var entity = UserEntity.of("first", "last", email, "password", null);

        var existsBeforeSave = repository.existsByEmail(email);
        assertFalse(existsBeforeSave);

        repository.save(entity);

        var existsAfterSave = repository.existsByEmail(email);
        assertTrue(existsAfterSave);
    }

    @Test
    void findByActivationKey() {
        var email = Email.of("test@test.com");
        var entity = UserEntity.of("first", "last", email, "password", null);
        var activationKey = entity.getActivationKey();

        var beforeSave = repository.findByActivationKey(activationKey);
        assertTrue(beforeSave.isEmpty());

        repository.save(entity);

        var afterSave = repository.findByActivationKey(activationKey);
        assertTrue(afterSave.isPresent());
    }
}