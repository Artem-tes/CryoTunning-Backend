package com.cryotunning.cryotunning.entities.requestdto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(name = "username",
    description = "Создается для будущей работы с приложением," +
            "аналог - ID",example = "@username",minimum = "4",maximum = "15")
    @NotNull
    @Size(min = 4,max = 15)
    private String username;

    @Schema(name = "password",description = "С помощью этого поля осуществляется вход"
    ,example = "the_secret",minimum = "8",maximum = "40")
    @NotNull
    @Size(min = 8,max = 40)
    private String password;
}
