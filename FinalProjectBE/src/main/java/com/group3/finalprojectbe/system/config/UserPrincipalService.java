package com.group3.finalprojectbe.system.config;

import com.group3.finalprojectbe.system.entity.Permission;
import com.group3.finalprojectbe.system.entity.Role;
import com.group3.finalprojectbe.system.entity.User;
import com.group3.finalprojectbe.system.entity.UserPrincipal;
import com.group3.finalprojectbe.system.excption.ExceptionString;
import com.group3.finalprojectbe.system.repo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class UserPrincipalService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(ExceptionString.USER_NOT_FOUND + " by the username " + username));

        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            for (Permission permission : role.getPermissions()) {
                authorities.add(new SimpleGrantedAuthority(permission.getName()));
            }
        }
        return new UserPrincipal(user, authorities);
    }
}
