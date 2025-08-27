package com.signalement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Intervention {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "municipality_id")
    private Municipality municipality;

    @ManyToOne
    @JoinColumn(name = "municipal_agent_id")
    private MunicipalAgent municipalAgent;

    private Date interventionDate;

    @Enumerated(EnumType.STRING)
    private StatusIntervention status;

    private String comment;
}
