package com.cryotunning.cryotunning.entities.responsesto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDTO {
    @Schema(name = "status",example = "400")
    private Integer status;
    @Schema(name = "errorMessage",example = "Very very bad exception")
    private String message;
}
