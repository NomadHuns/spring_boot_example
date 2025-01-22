package com.example.spring_boot_jpa_example._core.utils;

import com.example.spring_boot_jpa_example._core.exception.RestException403;
import com.example.spring_boot_jpa_example._core.exception.ExceptionMessage;
import com.example.spring_boot_jpa_example._core.security.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class AuthorityCheckUtils {

    public static boolean authorityCheck(CustomUserDetails customUserDetails, String userRole) {
        // 권한 체크
        if (customUserDetails.getAuthorities().stream()
                .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(userRole))) {

            throw new RestException403(ExceptionMessage.COMMON_AUTHORITY_FAIL.getMessage());
        }

        return true;
    }

    public static boolean authorityCheck(CustomUserDetails customUserDetails, List<String> userRole) {

        var authorities = customUserDetails.getAuthorities().stream()
                .filter(grantedAuthority -> userRole.contains(grantedAuthority.getAuthority())).toList();

        if (!authorities.isEmpty()) {
            return true;
        } else {
            throw new RestException403(ExceptionMessage.COMMON_AUTHORITY_FAIL.getMessage());
        }
    }
}
