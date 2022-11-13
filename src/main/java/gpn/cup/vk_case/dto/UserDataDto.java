package gpn.cup.vk_case.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Тело запроса на регистрацию в приложении
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDataDto {
    /**
     * Логин
     */
    @NotBlank
    private String username;
    /**
     * Пароль
     */
    @NotBlank
    private String password;
}
