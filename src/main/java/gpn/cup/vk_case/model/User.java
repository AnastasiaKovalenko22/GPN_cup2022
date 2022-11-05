package gpn.cup.vk_case.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @NotBlank
    @Column(name = "username", nullable = false)
    @Id
    private String username;

    @NotBlank
    @Column(name = "password", nullable = false)
    private String encodedPassword;
}
