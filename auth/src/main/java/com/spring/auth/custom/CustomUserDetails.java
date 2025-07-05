package com.spring.auth.custom;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.auth.Exception.userException;
import com.spring.auth.Module.userModule;
import com.spring.auth.Repo.userRepo;

@Service
public class CustomUserDetails implements UserDetailsService {

    @Autowired private userRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        userModule ud = userRepo.findByEmail(username);
        if (ud == null) {
            throw new userException("user email id invalid : " + username);
        }
        return new User(ud.getEmail(), ud.getPass(), new ArrayList<>());
    }
    
}
