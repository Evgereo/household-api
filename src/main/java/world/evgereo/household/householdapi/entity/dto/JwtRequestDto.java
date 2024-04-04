package world.evgereo.household.householdapi.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record JwtRequestDto(
        @NotBlank(message = "Name should be not blank")
        @Size(min = 4, max = 255, message = "The length of name should between 2 and 100")
        String name,

        @NotBlank(message = "password should be not blank")
        @Size(min = 4, max = 255, message = "The length of password should between 4 and 255")
        String password) {
}
