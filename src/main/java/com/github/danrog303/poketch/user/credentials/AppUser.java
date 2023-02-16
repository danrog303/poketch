package com.github.danrog303.poketch.user.credentials;

import com.github.danrog303.poketch.user.details.AppUserDetails;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AppUserRole role;

    @Email
    @Column(unique=true)
    private String email;

    @Column
    private String password;

    @Column
    private Boolean locked;

    @Column
    private Boolean enabled;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn
    private AppUserDetails details;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var authority = new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public AppUserDetails getDetails() {
        return details;
    }

    public String getEmail() {
        return email;
    }

    public AppUser(String email, String password, AppUserRole role, AppUserDetails details) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.details = details;

        this.locked = false;
        this.enabled = false;
    }
}
