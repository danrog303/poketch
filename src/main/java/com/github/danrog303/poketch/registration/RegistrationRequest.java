package com.github.danrog303.poketch.registration;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class RegistrationRequest {
    @Email
    @Length(max=512)
    @NotEmpty
    private String email;

    @Length(min=8, max=64)
    @NotEmpty
    private String password;

    @Length(min=4, max=64)
    @NotEmpty
    private String nickname;
}
