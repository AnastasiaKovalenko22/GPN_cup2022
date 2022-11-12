package gpn.cup.vk_case.controller;

import gpn.cup.vk_case.dto.RequestMembershipDto;
import gpn.cup.vk_case.dto.VkResponseDto;
import gpn.cup.vk_case.exception.NoUserException;
import gpn.cup.vk_case.exception.VkApiException;
import gpn.cup.vk_case.service.VkApiService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class VkApiController {
    private final VkApiService vkApiService;

    public VkApiController(VkApiService vkApiService) {
        this.vkApiService = vkApiService;
    }

    @GetMapping("isMember")
    public ResponseEntity<Object> isMember(@RequestHeader(name = "vk_service_token") String vkServiceToken,
                                           @Valid @RequestBody RequestMembershipDto request){
        try {
            Map<String, String> firstLastAndMiddleName =
                    vkApiService.getFirstLastAndMiddleNameFromVk(request.getUserId(), vkServiceToken);
            Boolean isMember = vkApiService.vkApiIsMember(request.getUserId(), request.getGroupId(), vkServiceToken);
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
