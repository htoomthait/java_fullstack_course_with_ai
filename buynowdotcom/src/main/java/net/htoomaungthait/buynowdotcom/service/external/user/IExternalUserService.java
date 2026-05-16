package net.htoomaungthait.buynowdotcom.service.external.user;

import net.htoomaungthait.buynowdotcom.dto.request.ExternalUserRequest;
import net.htoomaungthait.buynowdotcom.dto.response.ExternalUserDto;

import java.util.List;

public interface IExternalUserService {


    List<ExternalUserDto> getListOfExternalUsers();

    ExternalUserDto getExternalUserById(Long userId);

    ExternalUserDto createExternalUser(ExternalUserRequest newUser);

    ExternalUserDto updateExternalUserById(Long id, ExternalUserRequest userToUpdate);

    ExternalUserDto deleteExternalUserById(Long id);
}
