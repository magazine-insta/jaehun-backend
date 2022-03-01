package pbl.magazine.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pbl.magazine.dto.SignupRequestDto;
import pbl.magazine.model.User;
import pbl.magazine.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public User registerUser(SignupRequestDto requestDto) {

        if (userRepository.findByUsername(requestDto.getUsername()).orElse(null) != null) {
            throw new AccessDeniedException("이미 가입되어 있는 유저입니다.");
        }

        User user = User.builder()
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .nickname(requestDto.getNickname())
                .build();

        return userRepository.save(user);
    }
}
