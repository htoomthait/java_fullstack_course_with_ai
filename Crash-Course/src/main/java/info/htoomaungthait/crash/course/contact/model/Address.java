package info.htoomaungthait.crash.course.contact.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String country;

    private String city;

    private String address;

    private String postalCode;


}
