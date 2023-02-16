package com.github.danrog303.poketch.auth;

import com.github.danrog303.poketch.user.credentials.AppUser;
import com.github.danrog303.poketch.user.details.AppUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Facade class for obtaining {@link AppUser} and {@link AppUserDetails}
 * of currently logged-in user.
 */
@Service
public class AppUserProvider {
    /**
     * Gets {@link AppUser} object of currently logged-in user.
     */
    public AppUser getAppUser() {
        return (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * Gets {@link AppUserDetails} object of currently logged-in user.
     */
    public AppUserDetails getAppUserDetails() {
        return getAppUser().getDetails();
    }
}
