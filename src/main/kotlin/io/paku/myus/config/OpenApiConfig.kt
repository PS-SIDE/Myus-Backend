package io.paku.myus.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(
    info = Info(
        title = "TITLE",
        description = "DESCRIPTION"
    )
)
@Configuration
class OpenApiConfig