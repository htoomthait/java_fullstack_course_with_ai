package net.htoomaungthait.buynowdotcom.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.htoomaungthait.buynowdotcom.dto.response.Address;
import net.htoomaungthait.buynowdotcom.dto.response.CompanyDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExternalUserRequest {
    private String name;

    private String username;

    private String email;

    private Address address;

    private String phone;

    private String website;

    private CompanyDto company;
}
