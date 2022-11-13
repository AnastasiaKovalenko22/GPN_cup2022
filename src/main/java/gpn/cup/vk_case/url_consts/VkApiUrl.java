package gpn.cup.vk_case.url_consts;

/**
 * Ссылки для взаимодействия с VK Api
 */
public class VkApiUrl {

    /**
     * Ссылка для получения признака участника группы
     */
    public static final String IS_GROUP_MEMBER_URL =
            "https://api.vk.com/method/groups.isMember?user_id=%s&group_id=%s&v=5.131";

    /**
     * Ссылка для получения информации о пользователе
     */
    public static final String GET_USER_INFO_URL =
            "https://api.vk.com/method/users.get?user_id=%s&fields=nickname&v=5.131";
}
