package ru.babenko;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.babenko.dtos.AdminRegisterUserDto;
import ru.babenko.services.UsersService;

@Controller
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UsersService usersService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/register")
    public void register(@Valid @RequestBody AdminRegisterUserDto userDto) {
        usersService.register(userDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete")
    public void delete(@RequestParam("userId") Long userId) {
        usersService.delete(userId);
    }
}
