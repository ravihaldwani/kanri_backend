package com.kanrisoft.kanri.user.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(Email email);

    boolean existsByEmail(Email email);

    Optional<UserEntity> findByActivationKey(String key);
}