package com.lm_api.librarymanagementapi.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//this class tells springboot how to configure swagger
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI libraryApi(){
        return new OpenAPI().info(new Info().title("LIBRARY MANAGEMENT API")
                .version("1.0").description("rest api for managing books ,categores and loan").
                contact(new Contact().name("Sadaf Jabbar")));
    }
}
