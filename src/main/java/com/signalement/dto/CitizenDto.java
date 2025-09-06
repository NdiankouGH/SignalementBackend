package com.signalement.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitizenDto extends UserDto {

    @NotNull(message = "Le numéro de téléphone ne doit pas être nul.")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Le numéro de téléphone n'est pas valide.")
    private String phoneNumber;
    @NotNull(message = "L'adresse ne doit pas être nulle.")
    private String address;
    @NotNull(message = "La ville ne doit pas être nulle.")
    private String city;
}
