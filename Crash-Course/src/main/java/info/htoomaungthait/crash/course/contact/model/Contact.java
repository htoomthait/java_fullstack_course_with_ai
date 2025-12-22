package info.htoomaungthait.crash.course.contact.model;

import info.htoomaungthait.crash.course.contact.dto.request.ContactReq;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "contacts")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "First name must not be blank")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "Last name must not be blank")
    private String lastName;


    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number")
    private  String phoneNumber;

    @Embedded
    @Column(name = "address")
    private Address address;


    public static Contact of(ContactReq contactReq){
        return Contact.builder()
                .firstName(contactReq.getFirstName())
                .lastName(contactReq.getLastName())
                .email(contactReq.getEmail())
                .phoneNumber(contactReq.getPhoneNumber())
                .address(Address.builder()
                        .country(contactReq.getCountry())
                        .city(contactReq.getCity())
                        .address(contactReq.getAddress())
                        .postalCode(contactReq.getPostalCode())
                        .build())
                .build();
    }
}
