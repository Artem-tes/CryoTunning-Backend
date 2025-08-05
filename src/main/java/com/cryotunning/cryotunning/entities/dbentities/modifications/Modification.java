package com.cryotunning.cryotunning.entities.dbentities.modifications;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "modifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Modification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "engine_id")
    private Integer engine_id;
    @Column(name = "wheel_id")
    private Integer wheel_id;
    @Column(name = "body_id")
    private Integer body_id;
}
