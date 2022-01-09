package com.kanrisoft.kanri.user;

import com.kanrisoft.kanri.user.model.Role;
import com.kanrisoft.kanri.user.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
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
    @MappedCollection()
    private final Set<Role> roles;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    private Instant createdDate;

//    private Status status = Status.ACTIVE;

    //    private boolean verified;
    private UserEntity(String firstName, String lastName, String email, String password, String phone) {
        this(null, firstName, lastName, email, password, phone, Instant.now(), new HashSet<>());
    }

    @PersistenceConstructor
    private UserEntity(Long id, String firstName, String lastName, String email, String password, String phone, Instant createdDate, Set<Role> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.createdDate = createdDate;
        this.roles = roles;
    }

    //    private String designation;
    public static UserEntity of(String firstName, String lastName, String email, String password, String phone) {
        return new UserEntity(firstName, lastName, email, password, phone);
    }

    public boolean addRole(Role role) {
        return roles.add(role);
    }

    public boolean removeRole(Role role) {
        return roles.remove(role);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
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
        return true;
    }

}
