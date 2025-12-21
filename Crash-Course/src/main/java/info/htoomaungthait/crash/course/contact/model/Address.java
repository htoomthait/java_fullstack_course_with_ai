package info.htoomaungthait.crash.course.contact.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Address {

    private String country;

    private String city;

    private String address;

    private String postalCode;


}
