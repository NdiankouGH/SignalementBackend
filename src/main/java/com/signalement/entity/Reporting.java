package com.signalement.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reporting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date reportDate;
    private String title;
    private String description;
    private double latitude;
    private double longitude;
    private String address;

    @Enumerated(EnumType.STRING)
    private StatusSignalement status;

    @Enumerated(EnumType.STRING)
    private PrioritySignalement priority;

    @ManyToOne
    @JoinColumn(name = "citizen_id")
    private Citizen citizen;

    @ManyToOne
    @JoinColumn(name = "wreck_category_id")
    private WreckCategory category;

    @ManyToOne
    @JoinColumn(name = "municipality_id")
    private Municipality municipality;

    @ManyToOne
    @JoinColumn(name = "municipal_agent_id")
    private MunicipalAgent municipalAgent;

    @OneToMany(mappedBy = "reporting", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Photo> photos;
}
