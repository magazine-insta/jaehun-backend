package pbl.magazine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pbl.magazine.model.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select distinct p from Post p left join fetch p.user left join fetch p.likes")
    List<Post> findAllPostByFetchJoin();
}
