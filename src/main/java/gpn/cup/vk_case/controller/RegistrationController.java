package gpn.cup.vk_case.controller;

import gpn.cup.vk_case.dto.UserDataDto;
import gpn.cup.vk_case.model.User;
import gpn.cup.vk_case.security.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Контроллер регистрации
 */
@RestController
@RequestMapping("register")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Регистрация пользователя по логину и паролю
     * @param userDataDto - логин и пароль пользователя
     * @return - сообщение об успешной регистрации или
     * о том, что пользователь с таким логином уже существует
     */
    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Valid UserDataDto userDataDto){
        try {
            userService.loadUserByUsername(userDataDto.getUsername());
        }
        catch (UsernameNotFoundException ex){
            User user = new User(userDataDto.getUsername(),
                    new BCryptPasswordEncoder().encode(userDataDto.getPassword()));
            userService.save(user);
            return new ResponseEntity<>("Registration completed.", HttpStatus.OK);
        }
        return new ResponseEntity<>("User with this username already exists.", HttpStatus.BAD_REQUEST);
    }
}
