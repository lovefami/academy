package com.group3.finalprojectbe.system.config;

import com.group3.finalprojectbe.system.entity.UserPrincipal;
import com.group3.finalprojectbe.system.excption.BizException;
import com.group3.finalprojectbe.system.excption.BizExceptionKit;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecContext {
    public static Long getUserId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
            return userPrincipal.getId();
        }
        BizExceptionKit.of("get user id error").throwIt();
        return null;
    }
}
