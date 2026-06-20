package net.htoomaungthait.buynowdotcom.service.user.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.htoomaungthait.buynowdotcom.common.exception.custom.EntityExistsException;
import net.htoomaungthait.buynowdotcom.common.exception.custom.EntityNotFoundException;
import net.htoomaungthait.buynowdotcom.dto.request.UserRequest;
import net.htoomaungthait.buynowdotcom.dto.request.UserUpdateRequest;
import net.htoomaungthait.buynowdotcom.dto.response.UserCartOrderRespDto;
import net.htoomaungthait.buynowdotcom.dto.response.UserRespDto;
import net.htoomaungthait.buynowdotcom.model.Cart;
import net.htoomaungthait.buynowdotcom.model.Order;
import net.htoomaungthait.buynowdotcom.model.User;
import net.htoomaungthait.buynowdotcom.repository.CartRepository;
import net.htoomaungthait.buynowdotcom.repository.UserRepository;
import net.htoomaungthait.buynowdotcom.service.user.IUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;

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
    public User getUserMById(Long userId){
        return this.findById(userId);
    }

    @Override
    public UserCartOrderRespDto getUserCartOrderByUserId(Long userId) {
        User user = this.findById(userId);
        Optional<Cart> cart = cartRepository.findByUserId(userId);


        List<Order> orderList = user.getOrders();

        return UserCartOrderRespDto.fromModels(
                user,
                cart.orElse(null),
                orderList.size() == 0 ? List.of() : orderList
        );
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

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return Optional.ofNullable(this.userRepository.findByEmail(email))
                .orElseThrow(() -> new EntityNotFoundException("Authenticated user not found with email: " + email, "USR_003"));

    }


    private User findById(Long userId){
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: "+ userId, "USR_002"));
    }


}
