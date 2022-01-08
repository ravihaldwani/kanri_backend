package com.kanrisoft.kanri.user;

import com.kanrisoft.kanri.user.model.Role;
import com.kanrisoft.kanri.user.model.Status;
import com.kanrisoft.kanri.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Table("user")
public class UserEntity implements User {
    @Id
    private long id;

    private String firstName;

    private String lastName;

    private String phone;

    private String email;

    private String password;

    private Instant createdDate;

    @Transient
    private Status status;

    @Transient
    private boolean verified;

    @Transient
    private String designation;


    @Transient
    private byte[] userImage;

    @Transient
    private String userImageContentType;

    @Transient
    private Set<Role> roles = new HashSet<>();

    public boolean addRole(Role role) {
        return roles.add(role);
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
