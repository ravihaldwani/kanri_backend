package com.kanrisoft.kanri.user.model;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public interface User extends UserDetails {
    long getId();

    void setId(long id);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getPhone();

    void setPhone(String phone);

    java.time.Instant getCreatedDate();

    void setCreatedDate(java.time.Instant createdDate);

    Status getStatus();

    void setStatus(Status status);

    boolean isVerified();

    void setVerified(boolean verified);

    String getDesignation();

    void setDesignation(String designation);

    Set<Role> getRoles();

    void setRoles(Set<Role> roles);

    void setPassword(String password);

    void setUserImage(byte[] userImage);

    void setUserImageContentType(String userImageContentType);

    String getEmail();

    void setEmail(String email);
}
