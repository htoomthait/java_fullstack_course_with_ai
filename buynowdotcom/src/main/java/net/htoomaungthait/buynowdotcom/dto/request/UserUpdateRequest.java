package net.htoomaungthait.buynowdotcom.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateRequest {

    @NotBlank(message = "User's firstname cannot be blank.")
    private String firstName;

    @NotBlank(message = "User's lastname cannot be blank")
    private String lastName;

    @NotBlank(message = "User's email cannot be blank")
    private String email;

    @Size(min = 8, max = 100, message = "Password must be between 4 and 8 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, and one number"
    )
    private String password;
}
