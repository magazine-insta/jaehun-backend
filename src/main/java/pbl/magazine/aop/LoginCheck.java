package pbl.magazine.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoginCheck {

    @Before("execution(public * pbl.magazine.controller.PostController.changeLike(..))")
    public void onlyLoginAccess() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth.getPrincipal() instanceof UserDetails)) {
            throw new AccessDeniedException("로그인이 필요합니다");
        }
    }

    @Before("execution(public * pbl.magazine.controller.UserController..*(..))")
    public void onlyLogoutAccess() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof UserDetails) {
           throw new AccessDeniedException("이미 로그인이 되어있습니다");
        }
    }
}
