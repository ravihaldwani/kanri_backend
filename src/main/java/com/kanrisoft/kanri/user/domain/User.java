package com.kanrisoft.kanri.user.domain;

import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

public interface User extends UserDetails {
    Long getId();

    String getFirstName();

    String getLastName();

    boolean removeRole(Role role);

    boolean hasRole(Role role);

    Optional<PhoneNumber> getPhone();

    UserStatus getStatus();

    boolean isActivated();

    String getActivationKey();

    void activateUser(String activationKey);

    Set<Role> getRoles();

    Email getEmail();

    Instant getCreatedDate();
}