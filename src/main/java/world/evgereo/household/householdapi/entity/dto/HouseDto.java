package world.evgereo.household.householdapi.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record HouseDto(
        @NotBlank(message = "Address should be not blank")
        @Size(min = 4, max = 255, message = "The length of address should between 4 and 255")
        String address) {
}
