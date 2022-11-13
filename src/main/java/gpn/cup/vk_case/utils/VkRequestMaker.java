package gpn.cup.vk_case.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Класс для отправки запросов к VK Api
 */
@Component
public class VkRequestMaker {
    /**
     * Отправка запроса к VK Api
     * @param url - url запроса
     * @param vkServiceToken - сервисный ключ приложения VK
     * @return - строка ответа от VK в формате JSON
     */
    public String makeVkApiRequest(String url, String vkServiceToken){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(vkServiceToken);
        HttpEntity<Object> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);
        return response.getBody();
    }
}
