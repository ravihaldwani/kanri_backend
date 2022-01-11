package com.kanrisoft.kanri.config;

import com.kanrisoft.kanri.user.model.User;
import com.kanrisoft.kanri.user.model.UserId;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
class AuditorConfig implements AuditorAware<UserId> {

    public @NotNull Optional<UserId> getCurrentAuditor() {
        log.debug("Auditor aware called");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        var principal = authentication.getPrincipal();

        if (principal instanceof User) {
            return Optional.of(UserId.of(((User) principal).getId()));
        }
        return Optional.empty();
    }
}