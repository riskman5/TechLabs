package ru.babenko;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.babenko.dtos.Color;
import ru.babenko.dtos.FullCatDto;
import ru.babenko.dtos.InitialCatDto;
import ru.babenko.services.CatsService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cats")
@RequiredArgsConstructor
public class CatsController {
    private final CatsService catsService;

    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("/{catId}")
    public FullCatDto findCatById(@PathVariable("catId") Long catId) {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return catsService.findCatById(catId, username);
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping()
    public List<FullCatDto> findAllCats() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return catsService.findAllCats(username);
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @PostMapping()
    public void createCat(@Valid @RequestBody InitialCatDto catDto) {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        catsService.createCat(catDto, username);
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @DeleteMapping("/{catId}")
    public void deleteCatById(@PathVariable("catId") Long catId) {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        catsService.deleteCatById(catId, username);
    }


    @PreAuthorize("hasAuthority('OWNER')")
    @PutMapping("/friend")
    public void friendCats(@RequestParam("firstCatId") Long firstCatId,
                           @RequestParam("secondCatId") Long secondCatId) {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        catsService.friendCats(firstCatId, secondCatId, username);
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @PutMapping("/unfriend")
    public void unfriendCats(@RequestParam("firstCatId") Long firstCatId,
                             @RequestParam("secondCatId") Long secondCatId) {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        catsService.unfriendCats(firstCatId, secondCatId, username);
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("/filter")
    public List<FullCatDto> findCatsByParams(@RequestParam("color") Color color,
                                             @RequestParam("breed") String breed) {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return catsService.findCatsByParams(color, breed, username);
    }
}
