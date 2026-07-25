package com.secure.notes.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.secure.notes.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private static final Long serialVersionUID = 1L;
    private Long id;
    private String userName;
    private String email;
    @JsonIgnore
    private String password;
    private boolean is2faEnabled;
    private boolean isEnabled;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long userId, String userName, String email, String password, boolean twoFactorEnabled, List<? extends GrantedAuthority> grantedAuthority, boolean isEnabled) {
        this.id = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.is2faEnabled = twoFactorEnabled;
        this.authorities = grantedAuthority;
        this.isEnabled = isEnabled;
    }

    public static UserDetailsImpl build(User user) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole().toString());
        return new UserDetailsImpl(user.getUserId(), user.getUserName(), user.getEmail(), user.getPassword(), user.isTwoFactorEnabled(), List.of(grantedAuthority), user.isEnabled());
    }

    @Override
    public String getUsername() {
        return this.userName;
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
        return this.isEnabled;
    }
}
