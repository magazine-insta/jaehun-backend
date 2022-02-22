package pbl.magazine.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pbl.magazine.model.ErrorMessage;
import pbl.magazine.security.UserDetailsImpl;

@Aspect
@Component
public class LoginCheck {

    @Before("execution(public * pbl.magazine.controller.PostController.changeLike(..))")
    public void onlyLoginAccess() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth.getPrincipal() instanceof UserDetailsImpl)) {
            throw new AccessDeniedException("로그인이 필요합니다");
        }
    }

//    @Before
//    public ResponseEntity<ErrorMessage> onlyLogoutAccess() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth.getPrincipal() instanceof UserDetailsImpl) {
//
//        }
//    }
}
