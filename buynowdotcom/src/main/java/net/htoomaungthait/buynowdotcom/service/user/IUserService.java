package net.htoomaungthait.buynowdotcom.service.user;

import net.htoomaungthait.buynowdotcom.dto.request.UserRequest;
import net.htoomaungthait.buynowdotcom.dto.request.UserUpdateRequest;
import net.htoomaungthait.buynowdotcom.dto.response.UserRespDto;
import net.htoomaungthait.buynowdotcom.model.User;

import java.util.List;

public interface IUserService {

    UserRespDto createUser(UserRequest userToCreate);

    UserRespDto updateUserById(Long userId, UserUpdateRequest userRequestDto);

    UserRespDto getUserById(Long userId);

    UserRespDto deleteUserById(Long userId);

    List<UserRespDto> getAllUser();

    User getUserMById(Long userId);
}
