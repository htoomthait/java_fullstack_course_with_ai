package net.htoomaungthait.buynowdotcom.service.external.user.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.htoomaungthait.buynowdotcom.common.exception.custom.BadRequestException;
import net.htoomaungthait.buynowdotcom.common.exception.custom.EntityNotFoundException;
import net.htoomaungthait.buynowdotcom.common.exception.custom.GeneralException;
import net.htoomaungthait.buynowdotcom.dto.request.ExternalUserRequest;
import net.htoomaungthait.buynowdotcom.dto.response.ExternalUserDto;
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
                .uri("https://jsonplaceholder.typicode.com/users/{id}", userId)
                .exchangeToMono(response -> {

                    int status = response.statusCode().value();

                    if (response.statusCode().is2xxSuccessful()) {
                        log.info("Success fetching user {}, status: {}", userId, status);

                        return response.bodyToMono(ExternalUserDto.class);
                    }

                    if (response.statusCode().is4xxClientError()) {
                        log.error("Client error {}, status: {}", userId, status);
                        return Mono.error(new EntityNotFoundException(
                                "User not found with ID: " + userId,
                                "EXT_USER_004"
                        ));
                    }

                    if (response.statusCode().is5xxServerError()) {
                        log.error("Server error, status: {}", status);
                        return Mono.error(new GeneralException(
                                "External service error",
                                "EXT_USER_005"
                        ));
                    }

                    return Mono.error(new RuntimeException("Unexpected response"));
                })
                .block();





    }

    @Override
    public ExternalUserDto createExternalUser(ExternalUserRequest newUser) {

        return webClient.post()
                .uri("https://jsonplaceholder.typicode.com/users")
                .bodyValue(newUser)
                .retrieve()
                // Handle 4xx
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(String.class).flatMap(errorBody -> {
                            log.error("Client error while creating user: {}", errorBody);
                            return Mono.error(new BadRequestException(
                                    "Invalid request for creating user",
                                    "CER_004"
                            ));
                        })
                )
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                    log.error("Server error from external API: {}", clientResponse);
                    return Mono.error(new GeneralException(
                            "External service error",
                            "CER_006"
                    ));
                })
                .bodyToMono(ExternalUserDto.class)
                .doOnNext(user -> log.info("Fetched external user: {}", user))
                .block();
    }

    @Override
    public ExternalUserDto updateExternalUserById(Long id, ExternalUserRequest userToUpdate) {
        return webClient.put()
                .uri("https://jsonplaceholder.typicode.com/users/"+id)
                .bodyValue(userToUpdate)
                .retrieve()
                // Handle 4xx
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(String.class).flatMap(errorBody -> {
                            log.error("Client error while creating user: {}", errorBody);
                            return Mono.error(new BadRequestException(
                                    "Invalid request for updating user",
                                    "CER_004"
                            ));
                        })
                )
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                    log.error("Server error from external API: {}", clientResponse);
                    return Mono.error(new GeneralException(
                            "External service error",
                            "CER_006"
                    ));
                })
                .bodyToMono(ExternalUserDto.class)
                .doOnNext(user -> log.info("Fetched external user: {}", user))
                .block();

    }

    @Override
    public ExternalUserDto deleteExternalUserById(Long id) {

        ExternalUserDto userToDelete = this.getExternalUserById(id);

        webClient.delete()
                .uri("https://jsonplaceholder.typicode.com/users/"+id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    log.error("Client error while deleting user {}", id);
                    return Mono.error(new EntityNotFoundException(
                            "User not found with ID: " + id,
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
                .block();

        return userToDelete;
    }
}
