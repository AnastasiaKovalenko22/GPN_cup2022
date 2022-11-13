package gpn.cup.vk_case.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Тело ответа на запрос ФИО пользователя VK и признака участника группы VK
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"last_name", "first_name", "middle_name", "member"})
public class VkResponseDto {
    /**
     * Фамилия
     */
    @JsonProperty("last_name")
    private String lastName;
    /**
     * Имя
     */
    @JsonProperty("first_name")
    private String firstName;
    /**
     * Отчество
     */
    @JsonProperty("middle_name")
    private String middleName;
    /**
     * Признак участника группы VK
     */
    private Boolean member;
}
