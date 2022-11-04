package gpn.cup.vk_case.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestMembershipDto {
    @JsonProperty("user_id")
    @Pattern(regexp = "[0-9]+")
    @NotBlank
    private String userId;
    @JsonProperty("group_id")
    @Pattern(regexp = "[0-9]+")
    @NotBlank
    private String groupId;
}
