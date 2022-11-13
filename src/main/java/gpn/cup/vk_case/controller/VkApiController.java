package gpn.cup.vk_case.controller;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import gpn.cup.vk_case.dto.RequestMembershipDto;
import gpn.cup.vk_case.dto.VkResponseDto;
import gpn.cup.vk_case.exception.NoUserException;
import gpn.cup.vk_case.exception.VkApiException;
import gpn.cup.vk_case.service.VkApiService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Контроллер получения данных от VK
 */
@RestController
public class VkApiController {
    private final VkApiService vkApiService;
    /**
     * Кэш
     */
    private final LoadingCache<String, VkResponseDto> cache =
            CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES)
                    .maximumSize(100)
                    .concurrencyLevel(10)
                    .build(new CacheLoader<>() {
                        @Override
                        public VkResponseDto load(String request) throws ExecutionException {
                            try {
                                String[] params = request.split(" ");
                                String userId = params[0];
                                String groupId = params[1];
                                String token = params[2];
                                Map<String, String> firstLastAndMiddleName =
                                        vkApiService.getFirstLastAndMiddleNameFromVk(userId, token);
                                Boolean isMember = vkApiService.vkApiIsMember(userId, groupId, token);
                                return new VkResponseDto(firstLastAndMiddleName.get("last_name"),
                                        firstLastAndMiddleName.get("first_name"),
                                        firstLastAndMiddleName.get("middle_name"), isMember);
                            }catch(VkApiException | NoUserException e){
                                throw new ExecutionException(e);
                            }
                        }
                    });

    public VkApiController(VkApiService vkApiService) {
        this.vkApiService = vkApiService;
    }

    /**
     * Получение ФИО пользователя VK и признака участника группы VK
     * @param vkServiceToken - сервисный ключ приложения VK
     * @param request - id пользователя VK и id группы VK
     * @return - ФИО пользователя VK и признак участника группы VK
     * или сообщение об ошибке
     */
    @GetMapping("isMember")
    public ResponseEntity<Object> isMember(@RequestHeader(name = "vk_service_token") String vkServiceToken,
                                           @Valid @RequestBody RequestMembershipDto request) {
        try {
            VkResponseDto result = cache.get(String.format("%s %s %s",
                    request.getUserId(), request.getGroupId(), vkServiceToken));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ExecutionException e) {
            String message = e.getCause().getMessage();
            message = message.substring(message.indexOf(":") + 2);
            if(message.equals("User not found")){
                return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
