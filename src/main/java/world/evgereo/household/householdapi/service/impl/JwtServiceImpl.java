package world.evgereo.household.householdapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import world.evgereo.household.householdapi.entity.User;
import world.evgereo.household.householdapi.entity.dto.JwtRequestDto;
import world.evgereo.household.householdapi.entity.dto.JwtResponseDto;
import world.evgereo.household.householdapi.security.jwt.JwtUtil;
import world.evgereo.household.householdapi.service.JwtService;
import world.evgereo.household.householdapi.service.UserService;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public JwtResponseDto getAccessToken(JwtRequestDto requestDto) {
        User user;
        try {
            user = userService.getUserByName(requestDto.name());
            if (passwordEncoder.matches(requestDto.password(), user.getPassword()))
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
            else
                throw new BadCredentialsException("Incorrect name or password has been entered");
        } catch (RuntimeException ex) {
            throw new BadCredentialsException("Incorrect name or password has been entered");
        }
        return new JwtResponseDto(jwtUtil.generateAccessToken(user));
    }
}
