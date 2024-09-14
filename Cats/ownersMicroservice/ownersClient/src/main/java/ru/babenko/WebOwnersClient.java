package ru.babenko;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.babenko.dtos.FullOwnerDto;
import ru.babenko.dtos.InitialOwnerDto;

import java.util.List;

@Component
public class WebOwnersClient implements OwnersClient {
    private final WebClient webClient;

    public WebOwnersClient(WebClient.Builder webClientBuilder,
                           @Value("${owners.base.url:http://localhost:9125}")
                           String ownersUrl) {
        this.webClient = webClientBuilder
                .defaultHeader("Content-Type", "application/json")
                .baseUrl(ownersUrl)
                .build();
    }

    @Override
    public FullOwnerDto findOwnerById(Long id) {
        return webClient.get()
                .uri("/api/v1/owners/{ownerId}", id)
                .retrieve()
                .bodyToMono(FullOwnerDto.class)
                .block();
    }

    @Override
    public List<FullOwnerDto> findAllOwners() {
        return webClient.get()
                .uri("/api/v1/owners")
                .retrieve()
                .bodyToFlux(FullOwnerDto.class)
                .collectList()
                .block();
    }

    @Override
    public FullOwnerDto createOwner(InitialOwnerDto owner) {
        return webClient.post()
                .uri("/api/v1/owners")
                .bodyValue(owner)
                .retrieve()
                .bodyToMono(FullOwnerDto.class)
                .block();
    }

    @Override
    public void addCatToOwner(Long ownerId, Long catId) {

    }

    @Override
    public void deleteCatFromOwner(Long ownerId, Long catId) {

    }

    @Override
    public void deleteOwner(Long id) {
        webClient.delete()
                .uri("/api/v1/owners/{ownerId}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
