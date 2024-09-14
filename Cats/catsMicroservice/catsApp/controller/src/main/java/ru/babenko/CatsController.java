package ru.babenko;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.babenko.dtos.FullCatDto;
import ru.babenko.dtos.InitialCatDto;
import ru.babenko.dtos.Color;
import ru.babenko.services.CatsService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cats")
@RequiredArgsConstructor
public class CatsController {
    private final CatsService catsService;

    @GetMapping("/{catId}")
    public FullCatDto findCatById(@PathVariable("catId") Long catId,
                                  @RequestParam Long ownerId) {
        return catsService.findCatById(catId, ownerId);
    }

    @GetMapping()
    public List<FullCatDto> findAllCats(@RequestParam Long ownerId) {
        return catsService.findAllCats(ownerId);
    }

    @PostMapping()
    public FullCatDto createCat(@Valid @RequestBody InitialCatDto catDto,
                                @RequestParam Long ownerId) {
        return catsService.createCat(catDto, ownerId);
    }

    @DeleteMapping("/{catId}")
    public void deleteCatById(@PathVariable("catId") Long catId,
                              @RequestParam Long ownerId) {
        catsService.deleteCat(catId, ownerId);
    }

    @PutMapping("/friend")
    public void friendCats(@RequestParam("firstCatId") Long firstCatId,
                           @RequestParam("secondCatId") Long secondCatId,
                           @RequestParam Long ownerId) {
        catsService.friendCats(firstCatId, secondCatId, ownerId);
    }

    @DeleteMapping("/unfriend")
    public void unfriendCats(@RequestParam("firstCatId") Long firstCatId,
                             @RequestParam("secondCatId") Long secondCatId,
                             @RequestParam Long ownerId) {
        catsService.unfriendCats(firstCatId, secondCatId, ownerId);
    }

    @GetMapping("/filter")
    public List<FullCatDto> findCatsByParams(@RequestParam("color") Color color,
                                             @RequestParam("breed") String breed,
                                             @RequestParam Long ownerId) {
        return catsService.findCatsByParams(color, breed, ownerId);
    }
}
