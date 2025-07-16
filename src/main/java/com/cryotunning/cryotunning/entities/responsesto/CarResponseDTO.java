package com.cryotunning.cryotunning.entities.responsesto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarResponseDTO {
    private Integer id;
    private String brand;
    private String model;
    private String generation;
    private String color;
}
