package com.cryotunning.cryotunning.entities.responsesto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarResponseDTO {
    @Schema(name = "id",example = "1")
    private Integer id;
    @Schema(name = "brand",example = "NISSAN")
    private String brand;
    @Schema(name = "model",example = "180SX")
    private String model;
    @Schema(name = "generation",example = "S13")
    private String generation;
    @Schema(name = "color",example = "RED")
    private String color;
}
