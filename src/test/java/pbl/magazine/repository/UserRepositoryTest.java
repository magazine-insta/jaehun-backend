package pbl.magazine.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import pbl.magazine.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("UserRepository findByUsername 테스트")
    void findByUsername() {
        //given
        String username = "kim";
        User user = User.builder()
                .username(username)
                .password("1234")
                .nickname("nickname")
                .build();
        userRepository.save(user);

        //when
        User findUser = userRepository.findByUsername(username).orElse(null);

        //then
        assert findUser != null;
        assertEquals(findUser.getUsername(), username);
    }

}