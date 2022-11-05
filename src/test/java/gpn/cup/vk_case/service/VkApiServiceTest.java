package gpn.cup.vk_case.service;

import gpn.cup.vk_case.exception.NoUserException;
import gpn.cup.vk_case.exception.VkApiException;
import gpn.cup.vk_case.url_consts.VkApiUrl;
import gpn.cup.vk_case.utils.VkRequestMaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class VkApiServiceTest {

    private VkRequestMaker vkRequestMaker;
    private VkApiService vkApiService;

    @BeforeEach
    void setUp() {
        vkRequestMaker = Mockito.mock(VkRequestMaker.class);
        vkApiService = new VkApiService(vkRequestMaker);
    }

    @Test
    void getFirstLastAndMiddleNameFromVkSuccess() throws VkApiException, NoUserException {
        Mockito.when(vkRequestMaker.makeVkApiRequest(String.format(VkApiUrl.GET_USER_INFO_URL, "65748"),
                        "cc71bd04bb78ad04bb71bd0436b860e928bcb71db71bd04d915680827d5d52f5f0274ab"))
                .thenReturn("{\"response\":[{\"id\":65748,\"nickname\":\"Andreevich\"" +
                        ",\"first_name\":\"Ivan\",\"last_name\":\"Krylov\",\"can_access_closed\":false,\"is_closed\":true}]}");
        Map<String, String> expectedResult = Map.of("last_name", "Krylov",
                "first_name", "Ivan", "middle_name", "Andreevich");
        assertEquals(expectedResult, vkApiService.getFirstLastAndMiddleNameFromVk("65748",
                "cc71bd04bb78ad04bb71bd0436b860e928bcb71db71bd04d915680827d5d52f5f0274ab"));
    }

    @Test
    void getFirstLastAndMiddleNameFromVkNoMiddleName() throws VkApiException, NoUserException {
        Mockito.when(vkRequestMaker.makeVkApiRequest(String.format(VkApiUrl.GET_USER_INFO_URL, "65748"),
                        "cc71bd04bb78ad04bb71bd0436b860e928bcb71db71bd04d915680827d5d52f5f0274ab"))
                .thenReturn("{\"response\":[{\"id\":65748,\"nickname\":\"\"" +
                        ",\"first_name\":\"Ivan\",\"last_name\":\"Krylov\",\"can_access_closed\":false,\"is_closed\":true}]}");
        Map<String, String> expectedResult = new HashMap<>();
        expectedResult.put("last_name", "Krylov");
        expectedResult.put("first_name", "Ivan");
        expectedResult.put("middle_name", null);
        assertEquals(expectedResult, vkApiService.getFirstLastAndMiddleNameFromVk("65748",
                "cc71bd04bb78ad04bb71bd0436b860e928bcb71db71bd04d915680827d5d52f5f0274ab"));
    }

    @Test
    void getFirstLastAndMiddleNameFromVkUserNotFound(){
        Mockito.when(vkRequestMaker.makeVkApiRequest(String.format(VkApiUrl.GET_USER_INFO_URL, "6574800000000000000000000000"),
                        "cc71bd04bb78ad04bb71bd0436b860e928bcb71db71bd04d915680827d5d52f5f0274ab"))
                .thenReturn("{\"response\":[]}");
        assertThrows(NoUserException.class, () ->
                vkApiService.getFirstLastAndMiddleNameFromVk("6574800000000000000000000000",
                "cc71bd04bb78ad04bb71bd0436b860e928bcb71db71bd04d915680827d5d52f5f0274ab"));
    }

    @Test
    void getFirstLastAndMiddleNameFromVkInvalidToken(){
        Mockito.when(vkRequestMaker.makeVkApiRequest(String.format(VkApiUrl.GET_USER_INFO_URL, "65748"),
                        "ccTokenb"))
                .thenReturn("{\"error\":{\"error_code\":5,\"error_msg\":\"User authorization failed: invalid access_token (4).\"," +
                        "\"request_params\":[{\"key\":\"user_id\",\"value\":\"65748\"},{\"key\":\"fields\",\"value\":\"nickname\"}," +
                        "{\"key\":\"v\",\"value\":\"5.131\"},{\"key\":\"method\",\"value\":\"users.get\"},{\"key\":\"oauth\",\"value\":\"1\"}]}}");
        assertThrows(VkApiException.class, () ->
                vkApiService.getFirstLastAndMiddleNameFromVk("65748",
                        "ccTokenb"));
    }

    @Test
    void vkApiIsMemberReturnsTrue() throws VkApiException {
        Mockito.when(vkRequestMaker.makeVkApiRequest(String.format(VkApiUrl.IS_GROUP_MEMBER_URL, "65748", "93559769"),
                "cc71bd04bb78ad04bb71bd0436b860e928bcb71db71bd04d915680827d5d52f5f0274ab"))
                .thenReturn("{\"response\":1}");
        assertTrue(vkApiService.vkApiIsMember("65748", "93559769",
                "cc71bd04bb78ad04bb71bd0436b860e928bcb71db71bd04d915680827d5d52f5f0274ab"));
    }

    @Test
    void vkApiIsMemberReturnsFalse() throws VkApiException {
        Mockito.when(vkRequestMaker.makeVkApiRequest(String.format(VkApiUrl.IS_GROUP_MEMBER_URL, "65748", "93559769"),
                        "cc71bd04bb78ad04bb71bd0436b860e928bcb71db71bd04d915680827d5d52f5f0274ab"))
                .thenReturn("{\"response\":0}");
        assertFalse(vkApiService.vkApiIsMember("65748", "93559769",
                "cc71bd04bb78ad04bb71bd0436b860e928bcb71db71bd04d915680827d5d52f5f0274ab"));
    }

    @Test
    void vkApiIsMemberInvalidUserId(){
        Mockito.when(vkRequestMaker.makeVkApiRequest(String.format(VkApiUrl.IS_GROUP_MEMBER_URL, "657480000000000000", "93559769"),
                        "cc71bd04bb78ad04bb71bd0436b860e928bcb71db71bd04d915680827d5d52f5f0274ab"))
                .thenReturn("{\"error\":{\"error_code\":100,\"error_msg\":" +
                        "\"One of the parameters specified was missing or invalid: user_id not integer\"" +
                        ",\"request_params\":[{\"key\":\"user_id\",\"value\":\"657480000000000000\"}," +
                        "{\"key\":\"group_id\",\"value\":\"93559769\"},{\"key\":\"v\",\"value\":\"5.131\"}," +
                        "{\"key\":\"method\",\"value\":\"groups.isMember\"},{\"key\":\"oauth\",\"value\":\"1\"}]}}");
        assertThrows(VkApiException.class, () ->
                vkApiService.vkApiIsMember("657480000000000000", "93559769",
                        "cc71bd04bb78ad04bb71bd0436b860e928bcb71db71bd04d915680827d5d52f5f0274ab"));
    }

    @Test
    void vkApiIsMemberInvalidGroupId(){
        Mockito.when(vkRequestMaker.makeVkApiRequest(String.format(VkApiUrl.IS_GROUP_MEMBER_URL, "65748", "935597690000000000"),
                        "cc71bd04bb78ad04bb71bd0436b860e928bcb71db71bd04d915680827d5d52f5f0274ab"))
                .thenReturn("{\"error\":{\"error_code\":100,\"error_msg\":" +
                        "\"One of the parameters specified was missing or invalid: group_id not integer\"" +
                        ",\"request_params\":[{\"key\":\"user_id\",\"value\":\"65748\"}," +
                        "{\"key\":\"group_id\",\"value\":\"935597690000000000\"},{\"key\":\"v\",\"value\":\"5.131\"}," +
                        "{\"key\":\"method\",\"value\":\"groups.isMember\"},{\"key\":\"oauth\",\"value\":\"1\"}]}}");
        assertThrows(VkApiException.class, () ->
                vkApiService.vkApiIsMember("65748", "935597690000000000",
                        "cc71bd04bb78ad04bb71bd0436b860e928bcb71db71bd04d915680827d5d52f5f0274ab"));
    }

    @Test
    void vkApiIsMemberInvalidToken(){
        Mockito.when(vkRequestMaker.makeVkApiRequest(String.format(VkApiUrl.IS_GROUP_MEMBER_URL, "65748", "93559769"),
                        "cc71Token"))
                .thenReturn("{\"error\":{\"error_code\":5,\"error_msg\":\"User authorization failed: invalid access_token (4)." +
                        "\",\"request_params\":[{\"key\":\"user_id\"," +
                        "\"value\":\"65748\"},{\"key\":\"group_id\",\"value\":\"93559769\"}" +
                        ",{\"key\":\"v\",\"value\":\"5.131\"},{\"key\":\"method\",\"value\":\"groups.isMember\"}" +
                        ",{\"key\":\"oauth\",\"value\":\"1\"}]}}");
        assertThrows(VkApiException.class, () ->
                vkApiService.vkApiIsMember("65748", "93559769",
                        "cc71Token"));
    }
}