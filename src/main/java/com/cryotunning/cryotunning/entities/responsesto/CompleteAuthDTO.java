package com.cryotunning.cryotunning.entities.responsesto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteAuthDTO {
    @Schema(name = "jwtToken",example = "YFfbfjybYDY.773438yftsdBGYU.YBFUgysdb6")
    private String jwtToken;
    @Schema(name = "username",example = "@username")
    private String username;
}
