package com.github.danrog303.poketch.user.credentials;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public AppUser createUserAccount(AppUser user) {
        boolean present = appUserRepository.findByEmail(user.getEmail()).isPresent();
        if (present) {
            throw new EmailAlreadyExistsException("Email already taken");
        }
        appUserRepository.save(user);
        return user;
    }

    public void enableUserAccount(String userEmail) {
        appUserRepository.enable(userEmail);
    }
}
