package pbl.magazine.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    @Nested
    @DisplayName("유저 객체 생성")
    class CreateUser {
        private String username;
        private String password;
        private String nickname;

        @BeforeEach
        void setup() {
            username = "kim";
            password = "1234";
            nickname = "hodu";
        }

        @Nested
        @DisplayName("성공 케이스")
        class Success {
            @Test
            @DisplayName("정상 유저 생성자 테스트")
            void createUserByBuilder() {
                User user = User.builder()
                        .username(username)
                        .password(password)
                        .nickname(nickname)
                        .build();

                assertEquals(user.getUsername(), "kim");
                assertEquals(user.getPassword(), "1234");
                assertEquals(user.getNickname(), "hodu");
            }
        }

        @Nested
        @DisplayName("실패 케이스")
        class Fail {
            @Test
            @DisplayName("유저 생성자 예외 처리 테스트")
            void exception() {
                assertThrows(NullPointerException.class, () -> {
                    User.builder()
                            .username(username)
                            .nickname(nickname)
                            .build();
                }, "유저는 username, password, nickname 모두 필수이다");
            }
        }
    }

}