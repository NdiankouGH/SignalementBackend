package com.signalement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MunicipalAgentDto extends UserDto {

    @NotNull(message = "Le matricule ne doit pas être nul.")
    private String matricule;
    @NotNull(message = "La fonction ne doit pas être nulle.")
    private String function;
    @NotNull(message = "L'identifiant de la municipalité ne doit pas être nul.")
    private Long municipality_id;


}
