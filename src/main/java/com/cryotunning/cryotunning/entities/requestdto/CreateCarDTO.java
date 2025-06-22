package com.cryotunning.cryotunning.entities.requestdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarDTO {
    @NotNull
    @JsonProperty("brand")
    private String brand;
    @NotNull
    @JsonProperty("model")
    private String model;
    @NotNull
    @JsonProperty("generation")
    private String generation;
    @NotNull
    @JsonProperty("color")
    private String color;
}
