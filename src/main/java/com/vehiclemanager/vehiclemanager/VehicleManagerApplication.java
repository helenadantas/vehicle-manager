package com.vehiclemanager.vehiclemanager;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Vehicle Manager API", version = "1.0", description = "Library Management APIs."),
        security = {@SecurityRequirement(name = "bearerToken"), @SecurityRequirement(name = "cookie")})
@SecuritySchemes({
        @SecurityScheme(name = "bearerToken", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT"),
        @SecurityScheme(name = "cookie", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER, paramName = "cookie")
})
public class VehicleManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehicleManagerApplication.class, args);
    }

}
