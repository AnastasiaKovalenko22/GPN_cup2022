package gpn.cup.vk_case.controller;

import gpn.cup.vk_case.dto.RequestMembershipDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@RestController
public class RequestMembershipController {

    @GetMapping("isMember")
    public RedirectView isAGroupMember(@RequestHeader(name = "vk_service_token") String vkServiceToken,
                                       @Valid @RequestBody RequestMembershipDto request,
                                       RedirectAttributes attributes){
        attributes.addFlashAttribute("token", vkServiceToken);
        attributes.addAttribute("userId", request.getUserId());
        attributes.addAttribute("groupId", request.getGroupId());
        return new RedirectView("vk/api/isMember");
    }
}
