package gpn.cup.vk_case.service;

import gpn.cup.vk_case.exception.NoUserException;
import gpn.cup.vk_case.exception.VkApiException;
import gpn.cup.vk_case.url_consts.VkApiUrl;
import gpn.cup.vk_case.utils.CustomJsonParser;
import gpn.cup.vk_case.utils.VkRequestMaker;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Сервис взаимодействия с Vk Api
 */
@Service
public class VkApiService {

    private final VkRequestMaker vkRequestMaker;

    public VkApiService(VkRequestMaker vkRequestMaker) {
        this.vkRequestMaker = vkRequestMaker;
    }

    /**
     * Получение ФИО пользователя VK
     * @param userId - id пользователя VK
     * @param vkServiceToken - сервисный ключ приложения VK
     * @return - Map<String, String>, где ключи - (first_name, last_name, middle_name),
     * значения - (имя, фамилия, отчество) соответственно
     * @throws VkApiException - преобразованное исключение VK Api
     * @throws NoUserException - нет пользователя с указанным id пользователя VK
     */
    public Map<String, String> getFirstLastAndMiddleNameFromVk(String userId, String vkServiceToken)
            throws VkApiException, NoUserException {
        String responseBody = vkRequestMaker.makeVkApiRequest(String.format(VkApiUrl.GET_USER_INFO_URL, userId), vkServiceToken);
        if(responseBody.startsWith("error", 2)){
            CustomJsonParser.parseError(responseBody);
        }
        return CustomJsonParser.parseFirstAndLastName(responseBody);
    }

    /**
     * Получение признака участника группы VK
     * @param userId - id пользователя VK
     * @param groupId - id группы VK
     * @param vkServiceToken - сервисный ключ приложения VK
     * @return - true, если пользователь - участник группы, иначе false
     * @throws VkApiException - преобразованное исключение VK Api
     */
    public boolean vkApiIsMember(String userId, String groupId, String vkServiceToken) throws VkApiException {
        String responseBody = vkRequestMaker.makeVkApiRequest(String.format(VkApiUrl.IS_GROUP_MEMBER_URL, userId, groupId), vkServiceToken);
        if(responseBody.startsWith("error", 2)){
            CustomJsonParser.parseError(responseBody);
        }
        return CustomJsonParser.parseIsMemberResponse(responseBody);
    }

}
