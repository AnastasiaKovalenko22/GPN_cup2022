package gpn.cup.vk_case.repository;

import gpn.cup.vk_case.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * Таблица пользователей приложения в БД
 */
@Component
public interface UserRepository extends JpaRepository<User, String> {
    /**
     * Получение пользователя приложения по логину
     * @param username - логин
     * @return - пользователь приложения
     */
    User findByUsername(String username);
}
