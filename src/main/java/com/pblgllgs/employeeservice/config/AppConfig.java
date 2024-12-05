package com.pblgllgs.employeeservice.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 *
 * @author pblgl
 * Created on 25-11-2024
 *
 */
@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API EMPLOYEE")
                        .description("Documentación de la API con Springdoc OpenAPI")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("pblgllgs")
                                .url("https://www.pblgllgs.com")
                                .email("pblgllgs@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentación adicional")
                        .url("https://www.pblgllgs.com/documentacion"));
    }
}
