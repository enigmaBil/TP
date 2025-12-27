package com.enigma.tp_api_rest.infrastructure.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
        title = "TP API REST",
        version = "v1",
        description = "API REST for Task Planner Application",
        contact = @Contact(
            name = "Enigma Software House",
            email = "contact@liberateur.io",
            url = "https://liberateur.io"
        )
    ),
    servers = {
       @Server(
            description = "Local Environment",
            url = "http://localhost:8080"
       ),
       @Server(
            description = "Production Environment",
            url = "https://api.liberateur.io"
       ),
       @Server(
            description = "Staging Environment",
            url = "https://staging-api.liberateur.io"
       )
    },
    security = {
            @SecurityRequirement(name = "bearerAuth")
    }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        description = "JWT Bearer token authentication",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfiguration {}
