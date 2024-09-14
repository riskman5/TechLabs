package ru.babenko.services;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.babenko.JwtService;
import ru.babenko.dao.UsersDao;
import ru.babenko.dtos.AuthenticateUserDto;
import ru.babenko.dtos.AdminRegisterUserDto;
import ru.babenko.dtos.UserRegisterUserDto;
import ru.babenko.exceptions.AlreadyUsedUsernameException;
import ru.babenko.models.Authority;
import ru.babenko.models.User;
import ru.babenko.owners.AddOwnerToUserMessage;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersDao usersDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Override
    public String authenticate(AuthenticateUserDto initialUserDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(initialUserDto.username(), initialUserDto.password())
        );

        var user = usersDao.findByUsername(initialUserDto.username());

        return jwtService.generateToken(user);
    }

    @Override
    public void register(UserRegisterUserDto initialUserDto) {
        if (usersDao.findByUsername(initialUserDto.username()) != null) {
            throw new AlreadyUsedUsernameException();
        }

        var user = User.builder()
                .username(initialUserDto.username())
                .password(passwordEncoder.encode(initialUserDto.password()))
                .authorities(Set.of(Authority.USER))
                .build();

        usersDao.save(user);
    }

    @Override
    public void register(AdminRegisterUserDto initialUserDto)  {
        if (usersDao.findByUsername(initialUserDto.username()) != null) {
            throw new AlreadyUsedUsernameException();
        }

        var user = User.builder()
                .username(initialUserDto.username())
                .password(passwordEncoder.encode(initialUserDto.password()))
                .authorities(initialUserDto.authorities())
                .build();

        usersDao.save(user);
    }

    @Override
    public void delete(String username) {
        var user = usersDao.findByUsername(username);
        usersDao.delete(user);
    }

    @Override
    public void delete(Long userId) {
        usersDao.deleteById(userId);
    }

    @KafkaListener(topics = "add-owner-to-user")
    @Override
    public void addOwnerToUser(AddOwnerToUserMessage message) {
        var user = usersDao.findById(message.userId()).get();
        user.setOwnerId(message.ownerId());
        user.addAuthority(Authority.OWNER);
        usersDao.save(user);
    }
}
