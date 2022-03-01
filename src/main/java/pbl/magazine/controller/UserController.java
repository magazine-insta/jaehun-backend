package pbl.magazine.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pbl.magazine.dto.LoginRequestDto;
import pbl.magazine.dto.SignupRequestDto;
import pbl.magazine.model.ErrorMessage;
import pbl.magazine.model.User;
import pbl.magazine.security.jwt.JwtFilter;
import pbl.magazine.security.jwt.TokenProvider;
import pbl.magazine.service.UserService;
import pbl.magazine.util.SecurityUtil;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SecurityUtil securityUtil;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    // 로그인 요청 처리
    @PostMapping("/api/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDto requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword());


        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        User user = securityUtil.getCurrentUser().orElseThrow(
                () -> new IllegalStateException("로그인을 다시 한번 시도해주세요")
        );

        Map<String, String> userInfo = new HashMap<>();
        createUserInfo(userInfo, user, jwt);

        return ResponseEntity.ok().body(userInfo);
    }

    // 회원 가입 요청 처리
    @PostMapping("/api/signup")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignupRequestDto requestDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<ErrorMessage> errorMessages = new ArrayList<>();
            bindingResult.getAllErrors().forEach(objectError -> errorMessages.add(new ErrorMessage(objectError.getDefaultMessage())));
            return ResponseEntity.badRequest().body(errorMessages);
        }

        return ResponseEntity.ok().body(userService.registerUser(requestDto));
    }

    private void createUserInfo(Map<String, String> userInfo, User user, String jwt) {
        userInfo.put("token", jwt);
        userInfo.put("user_id", String.valueOf(user.getId()));
        userInfo.put("user_email", user.getUsername());
        userInfo.put("nickname", user.getNickname());
    }
}
