package pbl.magazine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pbl.magazine.model.Likes;
import pbl.magazine.model.Post;
import pbl.magazine.model.User;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByPostAndUser(Post post, User user);
}
