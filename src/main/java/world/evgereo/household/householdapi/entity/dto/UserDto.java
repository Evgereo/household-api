package world.evgereo.household.householdapi.entity.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDto(
        @NotBlank(message = "Name should be not blank")
        @Size(min = 4, max = 255, message = "The length of name should between 2 and 100")
        String name,

        @Min(value = 18, message = "You can't use the app if you're under 18 years old")
        @Max(value = 100, message = "You can't be over 100 years old")
        Integer age,

        @NotBlank(message = "password should be not blank")
        @Size(min = 4, max = 255, message = "The length of password should between 4 and 255")
        String password,

        @NotBlank(message = "password should be not blank")
        @Size(min = 4, max = 255, message = "The length of password should between 4 and 255")
        String passwordConfirm) {
}
