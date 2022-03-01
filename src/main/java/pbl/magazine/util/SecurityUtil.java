package pbl.magazine.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pbl.magazine.model.User;
import pbl.magazine.repository.UserRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

    private final UserRepository userRepository;

    public User getCurrentUserOrError() {
        Optional<User> currentUser = getCurrentUser();
        if (!currentUser.isPresent())
            throw new AccessDeniedException("로그인이 필요합니다");
        else
            return currentUser.get();
    }

    public Optional<User> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            return userRepository.findByUsername(userDetails.getUsername());
        } else return Optional.empty();
    }
}
