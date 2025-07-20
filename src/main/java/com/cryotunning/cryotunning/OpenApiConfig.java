package com.cryotunning.cryotunning;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "Сервис Тюннинга автомобилей API",
                description = "Main API",
                version = "1.0.0",
                contact = @Contact(
                        name = "Артем Потапов",
                        email = "strim20092509potapov@yandex.ru",
                        url = "https://github.com/ArtemPotapov52"
                )
        )
)
@SecurityScheme(
        name = "JWT",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {
}
