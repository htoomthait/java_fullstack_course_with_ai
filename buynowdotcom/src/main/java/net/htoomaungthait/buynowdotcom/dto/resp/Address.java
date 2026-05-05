package net.htoomaungthait.buynowdotcom.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address{
    
    private String street;

    private String suite;

    private String city;

    private  String zipcode;

    private GeoDto geo;
}
