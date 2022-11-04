package gpn.cup.vk_case.utils;

import com.google.gson.JsonObject;
import java.util.Map;

public class CustomJsonParser {
    public static Map<String, String> parseFirstAndLastName(String userInfo){
        JsonObject jsonUserInfo = new com.google.gson.JsonParser().parse(userInfo).getAsJsonObject();
        String fieldsStr = String.valueOf(jsonUserInfo.get("response"));
        JsonObject fields = new com.google.gson.JsonParser()
                .parse(fieldsStr.substring(1, fieldsStr.length() - 1))
                .getAsJsonObject();
        return Map.of("last_name", fields.get("last_name").getAsString(),
                "first_name", fields.get("first_name").getAsString(),
                "middle_name", fields.get("nickname").getAsString());
    }

    public static Boolean parseIsMemberResponse(String responseBody){
        JsonObject jsonResponseBody = new com.google.gson.JsonParser().parse(responseBody).getAsJsonObject();
        String strValueOfResponse = jsonResponseBody.get("response").getAsString();
        return strValueOfResponse.equals("1");
    }
}
