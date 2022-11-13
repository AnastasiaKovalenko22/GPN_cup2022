package gpn.cup.vk_case.exception;

/**
 * Исключение, возникающее когда по заданному id пользователя VK нет пользователя
 */
public class NoUserException extends Exception{
    public NoUserException(String message){
        super(message);
    }
}
