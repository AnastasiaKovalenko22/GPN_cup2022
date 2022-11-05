package gpn.cup.vk_case.service;

import gpn.cup.vk_case.exception.NoUserException;
import gpn.cup.vk_case.exception.VkApiException;
import gpn.cup.vk_case.url_consts.VkApiUrl;
import gpn.cup.vk_case.utils.CustomJsonParser;
import gpn.cup.vk_case.utils.VkRequestMaker;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class VkApiService {

    private final VkRequestMaker vkRequestMaker;

    public VkApiService(VkRequestMaker vkRequestMaker) {
        this.vkRequestMaker = vkRequestMaker;
    }

    public Map<String, String> getFirstLastAndMiddleNameFromVk(String userId, String vkServiceToken)
            throws VkApiException, NoUserException {
        String responseBody = vkRequestMaker.makeVkApiRequest(String.format(VkApiUrl.GET_USER_INFO_URL, userId), vkServiceToken);
        if(responseBody.startsWith("error", 2)){
            CustomJsonParser.parseError(responseBody);
        }
        return CustomJsonParser.parseFirstAndLastName(responseBody);
    }

    public boolean vkApiIsMember(String userId, String groupId, String vkServiceToken) throws VkApiException {
        String responseBody = vkRequestMaker.makeVkApiRequest(String.format(VkApiUrl.IS_GROUP_MEMBER_URL, userId, groupId), vkServiceToken);
        if(responseBody.startsWith("error", 2)){
            CustomJsonParser.parseError(responseBody);
        }
        return CustomJsonParser.parseIsMemberResponse(responseBody);
    }

}
