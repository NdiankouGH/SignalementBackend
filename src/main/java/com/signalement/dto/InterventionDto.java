package com.signalement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterventionDto {
    private Long id;
    @NotNull(message = "L'identifiant du signalement ne doit pas être nul.")
    private Long municipality_id;
    @NotNull(message = "L'identifiant de l'agent municipal ne doit pas être nul.")
    private Long municipal_agent_id;
    @NotNull(message = "La date d'intervention ne doit pas être nulle.")
    private Date interventionDate;
    @NotNull(message = "Le statut de l'intervention ne doit pas être nul.")
    private String status;
    private String comment;
    private Long reporting_id;
}
