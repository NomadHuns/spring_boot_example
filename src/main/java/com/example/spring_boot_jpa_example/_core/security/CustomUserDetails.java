package com.example.spring_boot_jpa_example._core.security;

import com.example.spring_boot_jpa_example.module.users.roles.UserRoles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/*
    인증 처리후 유저 객체가 저장될 객체 클래스
 */
public record CustomUserDetails(UserRoles userAuthority) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> userAuthority.getRole().getName());
        return authorities;
    }

    @Override
    public String getPassword() {
        return userAuthority.getUser().getPassword();
    }

    @Override
    public String getUsername() {
        return userAuthority.getUser().getUsername();
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