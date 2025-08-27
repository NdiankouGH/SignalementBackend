package com.signalement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WreekCategoryDto {

    private String name;
    private String description;
    private String color;
    private boolean active;
}
