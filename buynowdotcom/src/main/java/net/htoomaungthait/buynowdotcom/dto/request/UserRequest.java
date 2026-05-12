package net.htoomaungthait.buynowdotcom.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.htoomaungthait.buynowdotcom.model.User;
import net.htoomaungthait.buynowdotcom.model.UserRole;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequest {

    @NotBlank(message = "User's firstname cannot be blank.")
    private String firstName;

    @NotBlank(message = "User's lastname cannot be blank")
    private String lastName;

    @NotBlank(message = "User's email cannot be blank")
    @Email(message = "Please provide email with valid email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be between 4 and 8 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, and one number"
    )
    private String password;




    public static User userFromRequestDto(UserRequest userRequestDto){

        return User.builder()
                .firstName(userRequestDto.getFirstName())
                .lastName(userRequestDto.getLastName())
                .email(userRequestDto.getEmail())
                .password( userRequestDto.getPassword())
                .build();

    }

}
