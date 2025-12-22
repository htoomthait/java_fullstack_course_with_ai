package info.htoomaungthait.crash.course.contact.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ContactReq {

    @NotBlank(message = "First name must not be blank")
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    private String lastName;

    @NotBlank(message = "Email must not be blank")
    private String email;

    @NotBlank(message = "Phone number must not be blank")
    private String phoneNumber;


    @NotBlank(message = "Country must not be blank")
    private String country;

    @NotBlank(message = "City must not be blank")
    private String city;

    @NotBlank(message = "Address must not be blank")
    private String address;

    @NotBlank(message = "Postal code must not be blank")
    private String postalCode;

}
