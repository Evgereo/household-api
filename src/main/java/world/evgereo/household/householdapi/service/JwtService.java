package world.evgereo.household.householdapi.service;

import world.evgereo.household.householdapi.entity.dto.JwtRequestDto;
import world.evgereo.household.householdapi.entity.dto.JwtResponseDto;

public interface JwtService {
    JwtResponseDto getAccessToken(JwtRequestDto requestDto);
}
