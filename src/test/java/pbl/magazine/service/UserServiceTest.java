package pbl.magazine.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pbl.magazine.dto.SignupRequestDto;
import pbl.magazine.model.User;
import pbl.magazine.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    SignupRequestDto requestDto;
    UserService userService;

    @BeforeEach
    void setup() {
        requestDto = new SignupRequestDto();
        setSignupRequestDto(requestDto, "cloud@naver.com", "1234", "1234", "cloud");
        userService = new UserService(passwordEncoder, userRepository);
    }

    @Test
    @DisplayName("회원 가입 로직 정상 테스트")
    void serviceRegisterUser() {
        //given -> setup()

        //when
        User registerUser = userService.registerUser(requestDto);

        //then
        System.out.println("registerUser.getUsername() = " + registerUser.getUsername());
        assertEquals(registerUser.getId(), 1L);
        assertEquals(registerUser.getUsername(), requestDto.getUsername());
        assertEquals(registerUser.getNickname(), requestDto.getNickname());
//        assertEquals(registerUser.getPassword(), passwordEncoder.encode(requestDto.getPassword()));
        assertTrue(registerUser.isActivated());
    }


    @Test
    @DisplayName("중복 회원 검증 테스트")
    void duplicateUserTest() {
        //given
        SignupRequestDto dupRequstDto = new SignupRequestDto();
        setSignupRequestDto(dupRequstDto, "cloud@naver.com", "1234", "1234", "cloud");
        userService.registerUser(requestDto);

        //when
        //then
        assertThrows(AccessDeniedException.class, () -> userService.registerUser(dupRequstDto), "중복된 유저 네임은 가질 수 없다");
    }

    private void setSignupRequestDto(SignupRequestDto requestDto, String username, String password, String checkPassword, String cloud) {
        requestDto.setUsername(username);
        requestDto.setPassword(password);
        requestDto.setCheckPassword(checkPassword);
        requestDto.setNickname(cloud);
    }

}