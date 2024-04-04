package world.evgereo.household.householdapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.evgereo.household.householdapi.entity.dto.JwtRequestDto;
import world.evgereo.household.householdapi.entity.dto.JwtResponseDto;
import world.evgereo.household.householdapi.service.JwtService;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;

    @PostMapping(value = "/sign-in", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtResponseDto> getAccess(@RequestBody @Valid JwtRequestDto jwtRequestDto) {
        return ResponseEntity.ok(jwtService.getAccessToken(jwtRequestDto));
    }
}
