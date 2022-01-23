package com.kanrisoft.kanri.user.domain;

import com.kanrisoft.kanri.user.DatabaseIntegration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest extends DatabaseIntegration {

    @Autowired
    private UserRepository repository;

    @Test
    void shouldSaveAnEntity() {
        var email = Email.of("test@test.com");
        var entity = UserEntity.of("first", "last", email, "password", null);

        var savedOne = repository.save(entity);

        assertAll(
                () -> assertNotNull(savedOne.getId()),
                () -> Assertions.assertEquals(savedOne.getEmail(), entity.getEmail()),
                () -> Assertions.assertEquals(savedOne.getFirstName(), entity.getFirstName()),
                () -> Assertions.assertEquals(savedOne.getLastName(), entity.getLastName()),
                () -> Assertions.assertEquals(savedOne.getPassword(), entity.getPassword()),
                () -> Assertions.assertEquals(savedOne.getPhone(), entity.getPhone()),
                () -> Assertions.assertEquals(savedOne.getRoles(), entity.getRoles()),
                () -> Assertions.assertEquals(savedOne.getStatus(), entity.getStatus())
        );
    }

    @Test
    void shouldBeAbleToRetrieveAnEntity() {
        var email = Email.of("test@test.com");
        var entity = UserEntity.of("first", "last", email, "password", null);
        var savedOne = repository.save(entity);

        var searchedOne = repository.findByEmail(email);

        Assertions.assertEquals(savedOne, searchedOne.get());
    }

    @Test
    void findByEmail() {
        var email = Email.of("test@test.com");
        var entity = UserEntity.of("first", "last", email, "password", null);

        var beforeSave = repository.findByEmail(email);
        Assertions.assertTrue(beforeSave.isEmpty());

        repository.save(entity);

        var afterSave = repository.findByEmail(email);
        Assertions.assertTrue(afterSave.isPresent());
    }

    @Test
    void existsByEmail() {
        var email = Email.of("test@test.com");
        var entity = UserEntity.of("first", "last", email, "password", null);

        var existsBeforeSave = repository.existsByEmail(email);
        Assertions.assertFalse(existsBeforeSave);

        repository.save(entity);

        var existsAfterSave = repository.existsByEmail(email);
        Assertions.assertTrue(existsAfterSave);
    }

    @Test
    void findByActivationKey() {
        var email = Email.of("test@test.com");
        var entity = UserEntity.of("first", "last", email, "password", null);
        var activationKey = entity.getActivationKey();

        var beforeSave = repository.findByActivationKey(activationKey);
        Assertions.assertTrue(beforeSave.isEmpty());

        repository.save(entity);

        var afterSave = repository.findByActivationKey(activationKey);
        Assertions.assertTrue(afterSave.isPresent());
    }
}