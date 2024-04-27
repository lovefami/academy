package com.group3.finalprojectbe.system.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Getter
public class UserPrincipal implements UserDetails {

    private Long id;
    private String username;
    private String email;
    private Set<GrantedAuthority> authorities;
    public UserPrincipal(User user, Set<GrantedAuthority> authorities) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }



    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
