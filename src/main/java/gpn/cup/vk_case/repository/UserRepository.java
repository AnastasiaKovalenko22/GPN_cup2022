package gpn.cup.vk_case.repository;

import gpn.cup.vk_case.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}
