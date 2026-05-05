package net.htoomaungthait.buynowdotcom.service.external.user.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.htoomaungthait.buynowdotcom.common.exception.custom.EntityNotFoundException;
import net.htoomaungthait.buynowdotcom.common.exception.custom.GeneralException;
import net.htoomaungthait.buynowdotcom.dto.resp.ExternalUserDto;
import net.htoomaungthait.buynowdotcom.service.external.user.IExternalUserService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalUserService implements IExternalUserService {

    private final WebClient webClient;

    @Override
    public List<ExternalUserDto> getListOfExternalUsers() {



        ResponseEntity<List<ExternalUserDto>> response = webClient.get()
                .uri("https://jsonplaceholder.typicode.com/users")
                .retrieve()
                .toEntityList(ExternalUserDto.class)
                .block();

        List<ExternalUserDto> users = response.getBody();

        log.info("Users: {}", users);
        log.info("Headers: {}", response.getHeaders());
        log.info("Http Status: {}", response.getStatusCode());

        return users;


    }

    @Override
    public ExternalUserDto getExternalUserById(Long userId) {

        return webClient.get()
                .uri("https://jsonplaceholder.typicode.com/users/"+userId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    log.error("Client error while fetching user {}", userId);
                    return Mono.error(new EntityNotFoundException(
                            "User not found with ID: " + userId,
                            "EXT_USER_004"
                    ));
                })
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                    log.error("Server error from external API");
                    return Mono.error(new GeneralException(
                            "External service error",
                            "EXT_USER_005"
                    ));
                })
                .bodyToMono(ExternalUserDto.class)
                .doOnNext(user -> log.info("Fetched external user: {}", user))
                .block();





    }
}
