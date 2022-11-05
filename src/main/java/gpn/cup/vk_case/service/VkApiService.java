package gpn.cup.vk_case.service;

import gpn.cup.vk_case.exception.NoUserException;
import gpn.cup.vk_case.exception.VkApiException;
import gpn.cup.vk_case.utils.CustomJsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class VkApiService {
    public Map<String, String> getFirstLastAndMiddleNameFromVk(String url, String vkServiceToken)
            throws VkApiException, NoUserException {
        String responseBody = makeVkApiRequest(url, vkServiceToken);
        if(responseBody.startsWith("error", 2)){
            CustomJsonParser.parseError(responseBody);
        }
        return CustomJsonParser.parseFirstAndLastName(responseBody);
    }

    public boolean vkApiIsMember(String url, String vkServiceToken) throws VkApiException {
        String responseBody = makeVkApiRequest(url, vkServiceToken);
        if(responseBody.startsWith("error", 2)){
            CustomJsonParser.parseError(responseBody);
        }
        return CustomJsonParser.parseIsMemberResponse(responseBody);
    }

    private String makeVkApiRequest(String url, String vkServiceToken){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(vkServiceToken);
        HttpEntity<Object> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);
        return response.getBody();
    }
}
