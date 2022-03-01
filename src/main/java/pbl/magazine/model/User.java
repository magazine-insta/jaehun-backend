package pbl.magazine.model;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Validated
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = false, unique = true)
    @NotEmpty
    private String username;

    @Column(nullable = false)
    @NotEmpty
    private String nickname;

    @Column(nullable = false)
    @NotEmpty
    private String password;

    @Column
    private boolean activated;

    @Builder
    public User(@NonNull String username,@NonNull String nickname,@NonNull String password) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.activated = true;
    }
}
