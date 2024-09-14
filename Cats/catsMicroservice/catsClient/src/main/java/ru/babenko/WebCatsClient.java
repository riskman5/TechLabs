package ru.babenko;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.babenko.dtos.Color;
import ru.babenko.dtos.FullCatDto;
import ru.babenko.dtos.InitialCatDto;

import java.util.List;

@Component
public class WebCatsClient implements CatsClient {
    private final WebClient webClient;

    public WebCatsClient(WebClient.Builder webClientBuilder,
                        @Value("${cats.base.url:http://localhost:9124}")
                        String catsUrl) {
        this.webClient = webClientBuilder
                .defaultHeader("Content-Type", "application/json")
                .baseUrl(catsUrl)
                .build();
    }


    @Override
    public FullCatDto findCatById(Long catId, Long ownerId) {
        return webClient.get()
                .uri("/api/v1/cats/{catId}?ownerId={ownerId}", catId, ownerId)
                .retrieve()
                .bodyToMono(FullCatDto.class)
                .block();
    }

    @Override
    public List<FullCatDto> findAllCats(Long ownerId) {
        return webClient.get()
                .uri("/api/v1/cats?ownerId={ownerId}", ownerId)
                .retrieve()
                .bodyToFlux(FullCatDto.class)
                .collectList()
                .block();
    }

    @Override
    public FullCatDto createCat(InitialCatDto createCatDto, Long ownerId) {
        return webClient.post()
                .uri("/api/v1/cats?ownerId={ownerId}", ownerId)
                .bodyValue(createCatDto)
                .retrieve()
                .bodyToMono(FullCatDto.class)
                .block();
    }

    @Override
    public void deleteCatById(Long ownerId) {
        webClient.delete()
                .uri("/api/v1/cats?ownerId={ownerId}", ownerId)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public void friendCats(Long catId1, Long catId2, Long ownerId) {
        webClient.put()
                .uri("/api/v1/cats/friend?firstCatId={catId1}&secondCatId={catId2}&ownerId={ownerId}", catId1, catId2, ownerId)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public void unfriendCats(Long catId1, Long catId2, Long ownerId) {
        webClient.delete()
                .uri("/api/v1/cats/unfriend?firstCatId={catId1}&secondCatId={catId2}&ownerId={ownerId}", catId1, catId2, ownerId)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public List<FullCatDto> findCatsByParams(Color color, String breed, Long ownerId) {
        return webClient.get()
                .uri("/api/v1/cats/filter?color={color}&breed={breed}&ownerId={ownerId}", color, breed, ownerId)
                .retrieve()
                .bodyToFlux(FullCatDto.class)
                .collectList()
                .block();
    }
}
