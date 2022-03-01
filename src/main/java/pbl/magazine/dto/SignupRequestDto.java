package pbl.magazine.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class SignupRequestDto {

    @NotBlank(message = "값을 입력해주세요")
    @Email(message = "이메일 형식에 맞지 않습니다")
    private String username;

    @Size(min = 3, message = "닉네임은 최소 3글자 이상이어야 합니다")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "닉네임은 숫자와 영어 대소문자만 가능합니다")
    @NotBlank(message = "값을 입력해주세요")
    private String nickname;

    @Size(min = 4, message = "비밀번호는 최소 4글자 이상이어야 합니다")
    @NotBlank(message = "값을 입력해주세요")
    private String password;

    @NotBlank(message = "값을 입력해주세요")
    private String checkPassword;

    @AssertTrue(message = "비밀번호에 닉네임과 같은 값은 들어가면 안됩니다")
    public boolean isContaionNickname() {
        return !password.contains(nickname);
    }

    @AssertTrue(message = "입력한 비밀번호와 같지 않습니다")
    public boolean isSamePwd() {
        return password.equals(checkPassword);
    }
}
