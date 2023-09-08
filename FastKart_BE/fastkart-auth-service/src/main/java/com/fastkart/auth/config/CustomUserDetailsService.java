package com.fastkart.auth.config;

import com.fastkart.auth.config.CustomUserDetails;
import com.fastkart.auth.entity.UserInfo;
import com.fastkart.auth.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRespository userRespository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> user = userRespository.findByUserName(username);
        return user.map(CustomUserDetails::new).orElseThrow(()->new UsernameNotFoundException("username not found"));
    }
}
