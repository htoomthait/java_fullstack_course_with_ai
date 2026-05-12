package net.htoomaungthait.buynowdotcom.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import net.htoomaungthait.buynowdotcom.common.BaseController;
import net.htoomaungthait.buynowdotcom.common.response.ApiResponse;
import net.htoomaungthait.buynowdotcom.dto.request.UserRequest;
import net.htoomaungthait.buynowdotcom.dto.request.UserUpdateRequest;
import net.htoomaungthait.buynowdotcom.dto.resp.UserRespDto;
import net.htoomaungthait.buynowdotcom.service.user.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final IUserService userService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<UserRespDto>>> getAllUser(){

        List<UserRespDto> userRespDtos = this.userService.getAllUser();
        String statusCode = !userRespDtos.isEmpty() ? "USER_006" : "USER_001";

        return makeResponse(
                HttpStatus.OK.value(),
                statusCode,
                "success",
                getStatusMessageByCode(statusCode),
                userRespDtos
        );
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<UserRespDto>> createNewUser(
            @Valid
            @RequestBody
            UserRequest userRequest){

        String statusCode = "USER_003";
        return makeResponse(
                HttpStatus.CREATED.value(),
                statusCode,
                "success",
                getStatusMessageByCode(statusCode),
                this.userService.createUser(userRequest)
        );

    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserRespDto>> getUserById(
            @Valid
            @PathVariable
            @Min(value = 1, message = "Id must be greater than or equal to 1")
            Long userId
    ){
        String statusCode = "USER_005";

        return makeResponse(
                HttpStatus.OK.value(),
                statusCode,
                "success",
                getStatusMessageByCode(statusCode),
                this.userService.getUserById(userId)

        );
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserRespDto>> updateUserById(
            @Valid
            @PathVariable
            @Min(value = 1, message = "Id must be greater than or equal to 1")
            Long userId,
            @Valid
            @RequestBody UserUpdateRequest userUpdateRequest){

        String statusCode = "USER_004";

        return makeResponse(
                HttpStatus.OK.value(),
                statusCode,
                "success",
                getStatusMessageByCode(statusCode),
                this.userService.updateUserById(userId, userUpdateRequest)
        );
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserRespDto>> deleteUserById(
            @Valid
            @PathVariable
            @Min(value = 1, message = "Id must be greater than or equal to 1")
            Long userId){
        String statusCode = "USER_007";

        return makeResponse(
                HttpStatus.OK.value(),
                statusCode,
                "success",
                getStatusMessageByCode(statusCode),
                this.userService.deleteUserById(userId)
        );

    }



}
