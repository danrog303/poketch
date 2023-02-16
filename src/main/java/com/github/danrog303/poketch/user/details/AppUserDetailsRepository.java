package com.github.danrog303.poketch.user.details;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserDetailsRepository extends JpaRepository<AppUserDetails, Long> {
}
