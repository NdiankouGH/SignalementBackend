package com.signalement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MunicipalityDto {

    private Long id;
    @NotNull(message = "Le nom de la municipalité ne doit pas être nul.")
    private String name;
    private String description;
    @NotNull(message = "Le code de la municipalité ne doit pas être nul.")
    private String code;
    @NotNull(message = "La région ne doit pas être nulle.")
    private String region;
    @NotNull(message = "Le pays ne doit pas être nul.")
    private String country;
    @NotNull(message = "Le maire ne doit pas être nul.")
    private String mayor;


}
