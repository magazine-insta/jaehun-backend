package pbl.magazine.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pbl.magazine.dto.SignupRequestDto;
import pbl.magazine.model.ErrorMessage;
import pbl.magazine.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 로그인 페이지
    @GetMapping("/api/login")
    public String login() {
        return "login";
    }

    // 회원 가입 페이지
    @GetMapping("/api/signup")
    public String signup() {
        return "signup";
    }

    // 회원 가입 요청 처리
    @PostMapping("/api/signup")
    public ResponseEntity<Object> registerUser(@Valid SignupRequestDto requestDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<ErrorMessage> errorMessages = new ArrayList<>();
            bindingResult.getAllErrors().forEach(objectError -> {
            errorMessages.add(new ErrorMessage(objectError.getDefaultMessage()));
        });
            return ResponseEntity.badRequest().body(errorMessages);
        }

        userService.registerUser(requestDto);
        return ResponseEntity.ok().body("");
    }
}
