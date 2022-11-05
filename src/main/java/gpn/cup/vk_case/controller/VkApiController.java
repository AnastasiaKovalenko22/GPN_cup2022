package gpn.cup.vk_case.controller;

import gpn.cup.vk_case.dto.VkResponseDto;
import gpn.cup.vk_case.exception.NoUserException;
import gpn.cup.vk_case.exception.VkApiException;
import gpn.cup.vk_case.service.VkApiService;
import gpn.cup.vk_case.url_consts.VkApiUrl;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class VkApiController {
    private final VkApiService vkApiService;

    public VkApiController(VkApiService vkApiService) {
        this.vkApiService = vkApiService;
    }

    @GetMapping("vk/api/isMember")
    public ResponseEntity<Object> isMember(@ModelAttribute("token") String vkServiceToken,
                                                  @RequestParam(name = "userId") String userId,
                                                  @RequestParam(name = "groupId") String groupId){
        try {
            Map<String, String> firstLastAndMiddleName =
                    vkApiService.getFirstLastAndMiddleNameFromVk(String.format(VkApiUrl.GET_USER_INFO_URL, userId),
                            vkServiceToken);
            Boolean isMember = vkApiService.vkApiIsMember(String.format(VkApiUrl.IS_GROUP_MEMBER_URL, userId, groupId),
                    vkServiceToken);
            return new ResponseEntity<>(new VkResponseDto(firstLastAndMiddleName.get("last_name"),
                    firstLastAndMiddleName.get("first_name"),
                    firstLastAndMiddleName.get("middle_name"), isMember), HttpStatus.OK);
        }catch (VkApiException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (NoUserException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
