package com.cryotunning.cryotunning.entities.responsesto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteAuthDTO {
    private String jwtToken;
    private String username;
}
