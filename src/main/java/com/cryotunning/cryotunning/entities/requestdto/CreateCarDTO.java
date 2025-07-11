package com.cryotunning.cryotunning.entities.requestdto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private String brand;


    @NotNull
    @Size(min = 3,max = 12)
    @JsonProperty("model")
    private String model;


    @NotNull
    @Size(min = 3,max = 12)
    @JsonProperty("generation")
    private String generation;


    @NotNull
    @Size(min = 3,max = 12)
    @JsonProperty("color")
    private String color;
}
