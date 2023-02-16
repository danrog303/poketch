package com.github.danrog303.poketch.registration;

import com.github.danrog303.poketch.email.AppEmailService;
import com.github.danrog303.poketch.registration.token.RegistrationTokenService;
import com.github.danrog303.poketch.user.credentials.AppUser;
import com.github.danrog303.poketch.user.credentials.AppUserRole;
import com.github.danrog303.poketch.user.credentials.AppUserService;
import com.github.danrog303.poketch.user.details.AppUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final AppEmailService appEmailService;
    private final AppUserService appUserService;
    private final RegistrationTokenService registrationTokenService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(RegistrationRequest request) {
        var userDetails = new AppUserDetails(request.getNickname());
        var user = new AppUser(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                AppUserRole.ROLE_USER,
                userDetails
        );
        appUserService.createUserAccount(user);

        var token = registrationTokenService.createRandomTokenForUser(user);
        registrationTokenService.saveConfirmationToken(token);

        var confirmationEndpoint = "/auth/register/confirm?token=%s".formatted(token.getToken());
        var confirmationAbsoluteUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path(confirmationEndpoint).build().toUriString();
        appEmailService.sendRegistrationTokenEmail(request.getEmail(), confirmationAbsoluteUrl);
    }

    @Transactional
    public void confirmToken(String token) {
        var confirmationToken = registrationTokenService
                .getToken(token)
                .orElseThrow(() -> new RegistrationException("Token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new RegistrationException("Account already confirmed");
        }

        registrationTokenService.setTokenConfirmed(token);
        appUserService.enableUserAccount(confirmationToken.getUser().getEmail());
    }

    private String buildEmail(String link) {
        return "Twoj token: %s".formatted(link);
    }
}
