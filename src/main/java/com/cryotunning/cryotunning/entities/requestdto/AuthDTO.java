package com.cryotunning.cryotunning.entities.requestdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDTO {
    @NotNull
    @Size(min = 4,max = 15)
    private String username;
    @NotNull
    @Size(min = 8,max = 40)
    private String password;
}
