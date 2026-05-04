package net.htoomaungthait.buynowdotcom.service.external.user.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.htoomaungthait.buynowdotcom.service.external.user.IExternalUserService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalUserService implements IExternalUserService {

    private final WebClient webClient;

    @Override
    public void getListOfExternalUsers() {
        String response = webClient.get()
                .uri("https://jsonplaceholder.typicode.com/users")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info("API Response: {}", response);
    }
}
