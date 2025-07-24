package com.cryotunning.cryotunning.entities.requestdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarDTO {
    @NotNull
    @Size(min = 3,max = 12)
    @JsonProperty("brand")
    @Schema(name = "brand",example = "NISSAN")
    private String brand;


    @NotNull
    @Size(min = 3,max = 12)
    @JsonProperty("model")
    @Schema(name = "model",example = "180SX")
    private String model;


    @NotNull
    @Size(min = 3,max = 12)
    @JsonProperty("generation")
    @Schema(name = "generation",example = "S13")
    private String generation;


    @NotNull
    @Size(min = 3,max = 12)
    @JsonProperty("color")
    @Schema(name = "color",example = "RED")
    private String color;
}
