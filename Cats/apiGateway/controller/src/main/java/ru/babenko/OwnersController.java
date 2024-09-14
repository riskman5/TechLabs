package ru.babenko;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("/{ownerId}")
    public FullOwnerDto findOwnerById(@PathVariable("ownerId") Long ownerId) {
        return ownersService.findOwnerById(ownerId);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping()
    public List<FullOwnerDto> findAllOwners() {
        return ownersService.findAllOwners();
    }

    @PreAuthorize("hasAuthority('USER') and !hasAuthority('OWNER')")
    @PostMapping()
    public void createOwner(@Valid @RequestBody InitialOwnerDto ownerDto) {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        ownersService.createOwner(ownerDto, username);
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @DeleteMapping()
    public void deleteOwner() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        ownersService.deleteOwner(username);
    }
}
