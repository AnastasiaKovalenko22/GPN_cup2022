package gpn.cup.vk_case.controller;

import gpn.cup.vk_case.dto.VkResponseDto;
import gpn.cup.vk_case.utils.CustomJsonParser;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class VkApiController {

    private final String IS_GROUP_MEMBER_URL = "https://api.vk.com/method/groups.isMember?user_id=%s&group_id=%s&v=5.131";
    private final String GET_USER_INFO_URL = "https://api.vk.com/method/users.get?user_id=%s&fields=nickname&v=5.131";

    @GetMapping("vk/api/isMember")
    public ResponseEntity<VkResponseDto> isMember(@ModelAttribute("token") String vkServiceToken,
                                                  @RequestParam(name = "userId") String userId,
                                                  @RequestParam(name = "groupId") String groupId){
        Map<String, String> firstLastAndMiddleName = getFirstLastAndMiddleNameFromVk(String.format(GET_USER_INFO_URL, userId),
                vkServiceToken);
        Boolean isMember = vkApiIsMember(String.format(IS_GROUP_MEMBER_URL, userId, groupId), vkServiceToken);
        return new ResponseEntity<>(new VkResponseDto(firstLastAndMiddleName.get("last_name"),
                firstLastAndMiddleName.get("first_name"),
                firstLastAndMiddleName.get("middle_name"), isMember), HttpStatus.OK);
    }

    private Map<String, String> getFirstLastAndMiddleNameFromVk(String url, String vkServiceToken){
        String responseBody = makeVkApiRequest(url, vkServiceToken);
        return CustomJsonParser.parseFirstAndLastName(responseBody);
    }

    private boolean vkApiIsMember(String url, String vkServiceToken){
        String responseBody = makeVkApiRequest(url, vkServiceToken);
        return CustomJsonParser.parseIsMemberResponse(responseBody);
    }

    private String makeVkApiRequest(String url, String vkServiceToken){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(vkServiceToken);
        HttpEntity request = new HttpEntity(headers);
        return new RestTemplate().exchange(url, HttpMethod.GET, request, String.class).getBody();
    }
}
