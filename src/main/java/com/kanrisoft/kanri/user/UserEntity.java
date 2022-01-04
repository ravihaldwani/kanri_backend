package com.kanrisoft.kanri.user;

import com.kanrisoft.kanri.user.model.Role;
import com.kanrisoft.kanri.user.model.Status;
import com.kanrisoft.kanri.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.time.Instant;
import java.util.Collection;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
class UserEntity implements User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "status")
    private Status status;

    @Column(name = "verified")
    private boolean verified;

    @Column(name = "designation")
    private String designation;

    @Column(name = "password")
    private String password;

    @Lob
    @Column(name = "user_image")
    private byte[] userImage;

    @Column(name = "user_image_content_type")
    private String userImageContentType;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLES",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID")})
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }


//	reportsTo*
//	spaceId*

}
