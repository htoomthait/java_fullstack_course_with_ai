package net.htoomaungthait.buynowdotcom.service.user.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.htoomaungthait.buynowdotcom.common.exception.custom.EntityExistsException;
import net.htoomaungthait.buynowdotcom.common.exception.custom.EntityNotFoundException;
import net.htoomaungthait.buynowdotcom.dto.request.UserRequest;
import net.htoomaungthait.buynowdotcom.dto.request.UserUpdateRequest;
import net.htoomaungthait.buynowdotcom.dto.resp.UserRespDto;
import net.htoomaungthait.buynowdotcom.model.User;
import net.htoomaungthait.buynowdotcom.repository.UserRepository;
import net.htoomaungthait.buynowdotcom.service.user.IUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserRespDto createUser(UserRequest userToCreate) {

        User emailCheckingUser = this.userRepository.findByEmail(userToCreate.getEmail());

        if(emailCheckingUser != null){
            throw new EntityExistsException("Given email: "+ userToCreate.getEmail() + " already existed.", "USER_008");
        }

        User preparingUserToCreate = UserRequest.userFromRequestDto(userToCreate);

        String encodedPassword = passwordEncoder.encode(preparingUserToCreate.getPassword());
        preparingUserToCreate.setPassword(encodedPassword);

        User createdUser = this.userRepository.save(preparingUserToCreate);

        return UserRespDto.fromUser(createdUser);
    }

    @Override
    public UserRespDto updateUserById(Long userId, UserUpdateRequest userRequestDto) {

        log.info("User email to update is: "+ userRequestDto.getEmail());

        User existingUser = this.findById(userId);

        existingUser.setFirstName(userRequestDto.getFirstName());
        existingUser.setLastName(userRequestDto.getLastName());
        existingUser.setEmail(userRequestDto.getEmail());

        if(userRequestDto.getPassword() != null){
            // update user password
            existingUser.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

        }

        User updatedUser = this.userRepository.save(existingUser);

        return UserRespDto.fromUser(updatedUser);
    }

    @Override
    public UserRespDto getUserById(Long userId) {
        return UserRespDto.fromUser(this.findById(userId));
    }

    @Override
    public UserRespDto deleteUserById(Long userId) {
        User existingUser = this.findById(userId);

        this.userRepository.deleteById(userId);

        return UserRespDto.fromUser(existingUser);
    }

    @Override
    public List<UserRespDto> getAllUser() {

        List<User> userList = this.userRepository.findAll();
        return userList.stream()
                .map(UserRespDto::fromUser)
                .toList();
    }


    private User findById(Long userId){
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: "+ userId, "USR_002"));
    }


}
