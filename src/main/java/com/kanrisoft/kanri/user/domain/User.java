package com.kanrisoft.kanri.user.domain;

import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Set;

public interface User extends UserDetails {
    Long getId();

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getPhone();

    void setPhone(String phone);

    UserStatus getStatus();

    void setStatus(UserStatus status);

    boolean isActivated();

    String getActivationKey();

    void activateUser(String activationKey);

    Set<Role> getRoles();

    String getEmail();

    void setEmail(String email);

    void setPassword(String password);

    Instant getCreatedDate();

}