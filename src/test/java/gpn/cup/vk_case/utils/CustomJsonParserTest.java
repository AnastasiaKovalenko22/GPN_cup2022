package gpn.cup.vk_case.utils;

import gpn.cup.vk_case.exception.NoUserException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CustomJsonParserTest {

    @Test
    void parseFirstAndLastNameAllFields() throws NoUserException {
        Map<String, String> expectedResult = Map.of("last_name", "Krylov",
                "first_name", "Ivan", "middle_name", "Andreevich");
        assertEquals(expectedResult, CustomJsonParser.parseFirstAndLastName(
                "{\"response\":[{\"id\":65748,\"nickname\":\"Andreevich\"" +
                ",\"first_name\":\"Ivan\",\"last_name\":\"Krylov\",\"can_access_closed\":false,\"is_closed\":true}]}"));
    }

    @Test
    void parseFirstAndLastNameNoMiddleName() throws NoUserException {
        Map<String, String> expectedResult = new HashMap<>();
        expectedResult.put("last_name", "Krylov");
        expectedResult.put("first_name", "Ivan");
        expectedResult.put("middle_name", null);
        assertEquals(expectedResult, CustomJsonParser.parseFirstAndLastName(
                "{\"response\":[{\"id\":65748,\"nickname\":\"\"" +
                        ",\"first_name\":\"Ivan\",\"last_name\":\"Krylov\",\"can_access_closed\":false,\"is_closed\":true}]}"));
    }

    @Test
    void parseFirstAndLastNameNoUserInfo(){
        assertThrows(NoUserException.class, () -> CustomJsonParser.parseFirstAndLastName(
                "{\"response\":[]}"));
    }

    @Test
    void parseIsMemberResponseReturnsTrue() {
        assertTrue(CustomJsonParser.parseIsMemberResponse("{\"response\":1}"));
    }

    @Test
    void parseIsMemberResponseReturnsFalse() {
        assertFalse(CustomJsonParser.parseIsMemberResponse("{\"response\":0}"));
    }
}