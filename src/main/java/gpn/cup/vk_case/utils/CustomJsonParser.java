package gpn.cup.vk_case.utils;

import com.google.gson.JsonObject;
import gpn.cup.vk_case.exception.NoUserException;
import gpn.cup.vk_case.exception.VkApiException;

import java.util.HashMap;
import java.util.Map;

public class CustomJsonParser {
    public static Map<String, String> parseFirstAndLastName(String userInfo) throws NoUserException {
        JsonObject jsonUserInfo = new com.google.gson.JsonParser().parse(userInfo).getAsJsonObject();
        String fieldsStr = String.valueOf(jsonUserInfo.get("response"));
        try {
            JsonObject fields = new com.google.gson.JsonParser()
                    .parse(fieldsStr.substring(1, fieldsStr.length() - 1))
                    .getAsJsonObject();
        Map<String, String> result = new HashMap<>();
        result.put("last_name", fields.get("last_name").getAsString());
        result.put("first_name", fields.get("first_name").getAsString());
        String middle_name = fields.get("nickname").getAsString();
        if(middle_name.equals("")){
            result.put("middle_name", null);
        }else {
            result.put("middle_name", middle_name);
        }
        return result;
        }catch (IllegalStateException e){
            throw new NoUserException("User not found");
        }
    }

    public static Boolean parseIsMemberResponse(String responseBody){
        JsonObject jsonResponseBody = new com.google.gson.JsonParser().parse(responseBody).getAsJsonObject();
        String strValueOfResponse = jsonResponseBody.get("response").getAsString();
        return strValueOfResponse.equals("1");
    }

    public static void parseError(String responseBody) throws VkApiException {
        JsonObject jsonResponseBody = new com.google.gson.JsonParser().parse(responseBody).getAsJsonObject();
        JsonObject errorObject = jsonResponseBody.get("error").getAsJsonObject();
        String error_code = errorObject.get("error_code").getAsString();
        String error_msg = errorObject.get("error_msg").getAsString();
        throw new VkApiException("error_code: " + error_code + "\nerror_message: "+ error_msg);
    }
}
