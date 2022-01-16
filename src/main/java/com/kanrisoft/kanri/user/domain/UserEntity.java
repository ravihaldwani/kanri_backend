package com.kanrisoft.kanri.user.domain;

import com.kanrisoft.kanri.user.util.UserUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString(exclude = {"password"})
class UserEntity implements User {

    @Id
    private final Long id;
    @MappedCollection
    private final Set<Role> roles;
    private String activationKey;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    @CreatedDate
    private Instant createdDate;
    private UserStatus status;
    private boolean activated;

    @CreatedBy
    @Embedded.Nullable(prefix = "created_by_")
    private UserId createdBy;

    @LastModifiedBy
    @Embedded.Nullable(prefix = "last_modified_by_")
    private UserId lastModifiedBy;

    private UserEntity(
            Long id,
            String firstName,
            String lastName,
            String email,
            String password,
            String phone,
            Instant createdDate,
            Set<Object> roles,// NOTE: Set<Object> because the data jdbc deserializes to string
            UserStatus status,
            boolean activated,
            String activationKey,
            UserId createdBy,
            UserId lastModifiedBy
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.createdDate = createdDate;
        this.roles = UserUtils.mapToRoleSet(roles);
        this.status = status;
        this.activated = activated;
        this.activationKey = activationKey;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
    }

    public static UserEntity of(String firstName, String lastName, String email, String password, String phone) {
        var activationKey = UserUtils.generateActivationKey(email);
        return new UserEntity(
                null,
                firstName,
                lastName,
                email,
                password,
                phone,
                Instant.now(),
                new HashSet<>(),
                UserStatus.ACTIVE,
                false,
                activationKey,
                null,
                null
        );
    }

    @Override
    public String getActivationKey() {
        return this.activationKey;
    }

    @Override
    public void activateUser(String activationKey) {
        if (!activationKey.equals(this.activationKey)) {
            throw new IllegalStateException("Invalid activation key");
        }
        this.activated = true;
    }

    public boolean addRole(Role role) {
        return roles.add(role);
    }

    public boolean removeRole(Role role) {
        return roles.remove(role);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == UserStatus.ACTIVE;
    }

}
