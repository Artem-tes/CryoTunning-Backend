package com.cryotunning.cryotunning.entities.dbentities;

import com.cryotunning.cryotunning.entities.SaveEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "custom_cars")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarEntity extends SaveEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_owner")
    private Integer idOwner;

    @Column(name = "modification_id")
    private Integer idModification;

    @Column(name = "brand_id")
    private Integer brandId;

    @Column(name = "model_id")
    private Integer modelId;

    @Column(name = "generation_id")
    private Integer generationId;

    @Column(name = "color")
    private Integer colorId;


}
