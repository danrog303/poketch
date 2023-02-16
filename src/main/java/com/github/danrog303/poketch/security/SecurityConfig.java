package com.github.danrog303.poketch.security;

import com.github.danrog303.poketch.user.credentials.AppUserService;
import lombok.Setter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;

import java.util.HashMap;

@Configuration
public class SecurityConfig {
    @Getter @Setter(onMethod_={@Autowired})
    private AppUserService appUserService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(appUserService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        var handler = new ExceptionMappingAuthenticationFailureHandler();
        var exceptionMap = new HashMap<String, String>();
        exceptionMap.put(DisabledException.class.getName(), "/auth/login?accountDisabled");
        exceptionMap.put(BadCredentialsException.class.getName(), "/auth/login?error");
        handler.setExceptionMappings(exceptionMap);
        return handler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/register").anonymous()

                        .requestMatchers("/auth/logout").authenticated()
                        .requestMatchers("/pokemon/**", "/extras/**").authenticated()
                        .requestMatchers("/statistics").authenticated()

                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/", true)
                        .failureHandler(authenticationFailureHandler())
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout").logoutSuccessUrl("/auth/login?logout")
                );
        return http.build();
    }
}
