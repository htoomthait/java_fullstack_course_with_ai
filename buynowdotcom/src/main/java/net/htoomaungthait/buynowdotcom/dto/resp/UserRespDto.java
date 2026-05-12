package net.htoomaungthait.buynowdotcom.dto.resp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.htoomaungthait.buynowdotcom.model.User;
import net.htoomaungthait.buynowdotcom.model.UserRole;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRespDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Collection<UserRole> roles;


    public static UserRespDto fromUser(User user){

        return UserRespDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();
    }
}
