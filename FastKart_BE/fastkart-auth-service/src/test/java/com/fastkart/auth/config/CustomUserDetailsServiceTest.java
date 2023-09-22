package com.fastkart.auth.config;

import com.fastkart.auth.entity.UserInfo;
import com.fastkart.auth.exception.AuthException;
import com.fastkart.auth.repository.UserRespository;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRespository userRespository;

    @InjectMocks
    CustomUserDetailsService customUserDetailsService;

    @Test
    void loadUserByUsername_success(){
        Mockito.when(userRespository.findByUserName(any())).thenReturn(Optional.of(new UserInfo()));
        assertNotNull(customUserDetailsService.loadUserByUsername("testUser"));
    }

    @Test
    void loadUserByUsername_exception(){
        Mockito.when(userRespository.findByUserName(any())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () ->customUserDetailsService.loadUserByUsername("testUser"));
    }
}
