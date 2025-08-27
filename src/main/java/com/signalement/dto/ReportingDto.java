package com.signalement.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportingDto {


    @NotNull(message = "La date du rapport ne doit pas être nulle.")
    private Date reportDate;
    @NotNull(message = "Le titre du rapport ne doit pas être nul.")
    private String title;
    @NotNull(message = "La description du rapport ne doit pas être nulle.")
    private String description;
    @NotNull(message = "La latitude ne doit pas être nulle.")
    private double latitude;
    @NotNull(message = "La longitude ne doit pas être nulle.")
    private double longitude;
    @NotNull(message = "L'adresse ne doit pas être nulle.")
    private String adress;
    @NotNull(message = "Le statut ne doit pas être nul.")
    private String status;
    @NotNull(message = "La priorité ne doit pas être nulle.")
    private String priority;
    @NotNull(message = "L'identifiant du citoyen ne doit pas être nul.")
    private Long citizen_id;
    @NotNull(message = "L'identifiant de la catégorie du signalement ne doit pas être nul.")
    private Long wreek_category_id;
    @NotNull(message = "L'identifiant de la municipalité ne doit pas être nul.")
    private Long municipality_id;
    @NotNull(message = "L'identifiant de l'agent municipal ne doit pas être nul.")
    private Long municipal_agent_id;


}
