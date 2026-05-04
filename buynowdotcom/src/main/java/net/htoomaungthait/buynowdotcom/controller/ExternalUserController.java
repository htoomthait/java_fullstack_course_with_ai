package net.htoomaungthait.buynowdotcom.controller;

import lombok.AllArgsConstructor;
import net.htoomaungthait.buynowdotcom.common.BaseController;
import net.htoomaungthait.buynowdotcom.common.response.ApiResponse;
import net.htoomaungthait.buynowdotcom.service.external.user.IExternalUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/external-users")
@AllArgsConstructor
public class ExternalUserController extends BaseController {

    private final IExternalUserService externalUserService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<String>> getExternalUser(){

        externalUserService.getListOfExternalUsers();
        return makeResponse(
                200,
                "EXT_USER_001",
                "success",
                getStatusMessageByCode("EXT_USER_001"),
                "External User Data are logged in the console"
        );
    }
}
