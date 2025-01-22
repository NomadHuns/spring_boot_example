package com.example.spring_boot_jpa_example._core.security;

import com.example.spring_boot_jpa_example._core.exception.RestException401;
import com.example.spring_boot_jpa_example.module.users.roles.UserRoles;
import com.example.spring_boot_jpa_example.module.users.roles.UserRolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
    CustomUserDetails 객체를 불러오는데 사용되는 서비스 클래스.
    인증 처리를 할때 사용됩니다.
 */
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRolesRepository userRolesRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRoles userAuthorityPS = userRolesRepository.findByUser_Username(username).orElseThrow(
                () -> new RestException401("존재하지 않는 아이디입니다.")
        );
        return new CustomUserDetails(userAuthorityPS);
    }
}