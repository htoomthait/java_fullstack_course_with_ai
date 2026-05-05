package net.htoomaungthait.buynowdotcom.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExternalUserDto {

    private Long id;

    private String name;

    private String username;

    private String email;

    private Address address;

    private String phone;

    private String website;

    private CompanyDto company;

}

