package com.cryotunning.cryotunning.entities.dbentities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "models")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "model_name")
    private String modelName;
    @Column(name = "brand_id")
    private Integer brandId;
}
