package com.github.danrog303.poketch.registration.token;

import com.github.danrog303.poketch.user.credentials.AppUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationTokenService {
    private final RegistrationTokenRepository confirmationTokenRepository;

    @Transactional
    public Optional<RegistrationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    @Transactional
    public void saveConfirmationToken(RegistrationToken token) {
        confirmationTokenRepository.save(token);
    }

    public RegistrationToken createRandomTokenForUser(AppUser user) {
        var tokenStr = UUID.randomUUID().toString();
        var creationDate = LocalDateTime.now();
        return new RegistrationToken(tokenStr, creationDate, null, user);
    }

    @Transactional
    public void setTokenConfirmed(String token) {
        getToken(token).orElseThrow().setConfirmedAt(LocalDateTime.now());
    }
}
