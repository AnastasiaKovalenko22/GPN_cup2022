package gpn.cup.vk_case.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Тело запроса ФИО пользователя VK и признака участника группы VK
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestMembershipDto {
    /**
     * id пользователя
     */
    @JsonProperty("user_id")
    @Pattern(regexp = "[0-9]+")
    @NotBlank
    private String userId;
    /**
     * id группы
     */
    @JsonProperty("group_id")
    @Pattern(regexp = "[0-9]+")
    @NotBlank
    private String groupId;
}
