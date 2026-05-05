package net.htoomaungthait.buynowdotcom.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import net.htoomaungthait.buynowdotcom.common.BaseController;
import net.htoomaungthait.buynowdotcom.common.response.ApiResponse;
import net.htoomaungthait.buynowdotcom.dto.resp.ExternalUserDto;
import net.htoomaungthait.buynowdotcom.service.external.user.IExternalUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/external-users")
@AllArgsConstructor
public class ExternalUserController extends BaseController {

    private final IExternalUserService externalUserService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<ExternalUserDto>>> getExternalUsers(){

        externalUserService.getListOfExternalUsers();
        return makeResponse(
                200,
                "EXT_USER_001",
                "success",
                getStatusMessageByCode("EXT_USER_001"),
                externalUserService.getListOfExternalUsers()
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<ExternalUserDto>> getExternalUserById(
            @PathVariable
            @Valid
            @Min(value = 1, message = "Id must be greater than or equal to 1")
            Long userId
    ){
        return makeResponse(
                200,
                "EXT_USER_002",
                "success",
                getStatusMessageByCode("EXT_USER_002"),
                externalUserService.getExternalUserById(userId)
        );
    }
}
