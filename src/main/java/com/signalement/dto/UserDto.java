package com.signalement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    private Long id;
    @NotNull(message = "Le prénom ne doit pas être nul.")
    private String firstName;
    @NotNull(message = "Le nom de famille ne doit pas être nul.")
    private String lastName;
    @NotNull(message = "L'email ne doit pas être nul.")
    private String email;
    @NotNull(message = "Le mot de passe ne doit pas être nul.")
    private String password;
    @NotNull(message = "Le rôle ne doit pas être nul.")
    private String role; // e.g., "USER", "MUNICIPAL_AGENT", "ADMIN"
    @NotNull(message = "La date d'inscription ne doit pas être nulle.")
    private Date inscriptionDate;
    private boolean isActive;
}
