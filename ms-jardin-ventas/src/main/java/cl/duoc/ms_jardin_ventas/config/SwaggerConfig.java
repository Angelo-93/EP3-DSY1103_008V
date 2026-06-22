package cl.duoc.ms_jardin_ventas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Ventas - El Jardín del Ángel")
                        .version("1.0.0")
                        .description("Documentación oficial de los endpoints REST del microservicio transaccional de Ventas."));
    }
}