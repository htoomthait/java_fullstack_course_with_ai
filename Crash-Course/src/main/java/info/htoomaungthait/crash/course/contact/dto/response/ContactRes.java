package info.htoomaungthait.crash.course.contact.dto.response;

import info.htoomaungthait.crash.course.contact.model.Contact;
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
public class ContactRes {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String country;

    private String city;

    private String address;

    private String postalCode;

    public static ContactRes of(Long id, String firstName, String lastName, String email, String phoneNumber,
                                    String country, String city, String address, String postalCode) {
        return ContactRes.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phoneNumber(phoneNumber)
                .country(country)
                .city(city)
                .address(address)
                .postalCode(postalCode)
                .build();
    }

    public static ContactRes from(Contact contact){
        return ContactRes.builder()
                .id(contact.getId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .phoneNumber(contact.getPhoneNumber())
                .country(contact.getAddress().getCountry())
                .city(contact.getAddress().getCity())
                .address(contact.getAddress().getAddress())
                .postalCode(contact.getAddress().getPostalCode())
                .build();
    }
}
