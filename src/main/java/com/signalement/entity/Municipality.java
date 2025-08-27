package com.signalement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Municipality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;
    private String region;
    private String country;
    private String mayor;
    private boolean active;

    @OneToMany(mappedBy = "municipality", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MunicipalAgent> municipalAgents;

    @OneToMany(mappedBy = "municipality", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reporting> reports;
}
