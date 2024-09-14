package ru.babenko;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.babenko.dtos.AuthenticateUserDto;
import ru.babenko.dtos.UserRegisterUserDto;
import ru.babenko.services.UsersService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;

    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public void register(@Valid @RequestBody UserRegisterUserDto userDto) {
        usersService.register(userDto);
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/authenticate")
    public String authenticate(@RequestBody AuthenticateUserDto userDto) {
        return usersService.authenticate(userDto);
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping
    public void delete() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        usersService.delete(username);
    }
}
