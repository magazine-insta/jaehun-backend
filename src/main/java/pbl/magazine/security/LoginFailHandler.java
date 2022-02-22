package pbl.magazine.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import pbl.magazine.model.ErrorMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class LoginFailHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorMessage errorMessage;
        if (exception instanceof BadCredentialsException) {
            errorMessage = new ErrorMessage("닉네임과 패스워드를 확인해주세요");
            String result = objectMapper.writeValueAsString(errorMessage);
            response.getWriter().write(result);
        }
    }
}
