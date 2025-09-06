package com.signalement.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoDto {

    private Long id;
    @NotNull(message = "L'URL de la photo ne doit pas être nulle.")
    private String url;
    private String title;
    private Long reporting_id;
}
