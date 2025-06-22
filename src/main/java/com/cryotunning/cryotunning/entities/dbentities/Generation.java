package com.cryotunning.cryotunning.entities.dbentities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "generations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Generation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "generation_name")
    private String generationName;
    @Column(name = "model_id")
    private Integer id_model;
}
