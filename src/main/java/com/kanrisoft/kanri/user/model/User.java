package com.kanrisoft.kanri.user.model;

import java.time.Instant;
import java.util.Set;

public interface User {
    long getId();

    void setId(long id);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getPhone();

    void setPhone(String phone);

    Instant getCreatedDate();

    void setCreatedDate(Instant createdDate);

    Status getStatus();

    void setStatus(Status status);

    boolean isVerified();

    void setVerified(boolean verified);

    String getDesignation();

    void setDesignation(String designation);

    Set<Role> getRoles();

    void setRoles(Set<Role> roles);

    String getEmail();

    void setEmail(String email);

    void setPassword(String password);

    void setUserImage(byte[] userImage);

    void setUserImageContentType(String userImageContentType);
}
