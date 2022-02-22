package pbl.magazine.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pbl.magazine.dto.SignupRequestDto;
import pbl.magazine.model.User;
import pbl.magazine.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void registerUser(SignupRequestDto requestDto) {
        String userId = requestDto.getUsername();

        Optional<User> found = userRepository.findByUsername(userId);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }

        String userPwd = passwordEncoder.encode(requestDto.getPassword());
        String nickname = requestDto.getNickname();
        String userImg = requestDto.getUserImg();

        User user = new User(userId, userPwd, nickname, userImg);
        userRepository.save(user);
    }
}
