package net.htoomaungthait.buynowdotcom.service.external.user;

import net.htoomaungthait.buynowdotcom.dto.resp.ExternalUserDto;

import java.util.List;

public interface IExternalUserService {


    List<ExternalUserDto> getListOfExternalUsers();

    ExternalUserDto getExternalUserById(Long userId);
}
