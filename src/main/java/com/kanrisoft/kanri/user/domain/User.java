package com.kanrisoft.kanri.user.domain;

import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

public interface User extends UserDetails {
    Long getId();

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    Optional<PhoneNumber> getPhone();

    void setPhone(PhoneNumber phone);

    UserStatus getStatus();

    void setStatus(UserStatus status);

    boolean isActivated();

    String getActivationKey();

    void activateUser(String activationKey);

    Set<Role> getRoles();

    Email getEmail();

    void setEmail(Email email);

    void setPassword(String password);

    Instant getCreatedDate();

}