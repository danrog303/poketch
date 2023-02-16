package com.github.danrog303.poketch.registration.token;

import com.github.danrog303.poketch.user.credentials.AppUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class RegistrationToken {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique=true)
    private String token;

    @NotNull
    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime confirmedAt;

    @NotNull
    @ManyToOne
    @JoinColumn
    private AppUser user;

    public RegistrationToken(String token, LocalDateTime createdAt, LocalDateTime confirmedAt, AppUser user) {
        this.token = token;
        this.createdAt = createdAt;
        this.confirmedAt = confirmedAt;
        this.user = user;
    }

    public RegistrationToken() {
    }
}
