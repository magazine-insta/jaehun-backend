package pbl.magazine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableJpaAuditing
public class MagazineApplication {

    public static void main(String[] args) {
        SpringApplication.run(MagazineApplication.class, args);
    }

}
