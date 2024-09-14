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
import ru.babenko.dtos.FullOwnerDto;
import ru.babenko.dtos.InitialOwnerDto;
import ru.babenko.services.OwnersService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/owners")
@RequiredArgsConstructor
public class OwnersController {
    private final OwnersService ownersService;

    @GetMapping("/{ownerId}")
    public FullOwnerDto findOwnerById(@PathVariable("ownerId") Long ownerId) {
        return ownersService.findOwnerById(ownerId);
    }

    @GetMapping()
    public List<FullOwnerDto> findAllOwners() {
        return ownersService.findAllOwners();
    }

    @PostMapping()
    public FullOwnerDto createOwner(@Valid @RequestBody InitialOwnerDto ownerDto) {
        return ownersService.createOwner(ownerDto);
    }

    @DeleteMapping("/{ownerId}")
    public void deleteOwner(@PathVariable("ownerId") Long ownerId) {
        ownersService.deleteOwner(ownerId);
    }

    @PutMapping("cats")
    public void addCatToOwner(@RequestParam("ownerId") Long ownerId, @RequestParam("catId") Long catId) {
        ownersService.addCatToOwner(ownerId, catId);
    }

    @DeleteMapping("cats")
    public void deleteCatFromOwner(@RequestParam("ownerId") Long ownerId, @RequestParam("catId") Long catId) {
        ownersService.deleteCatFromOwner(ownerId, catId);
    }
}