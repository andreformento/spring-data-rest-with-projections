package com.formento.projections;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class UserSession {

    public User getUserSession() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
