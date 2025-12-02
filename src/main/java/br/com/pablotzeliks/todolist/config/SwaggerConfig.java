package br.com.pablotzeliks.todolist.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TodoList API")
                        .version("1.0.0")
                        .description("API para gerenciamento de tarefas construída com Spring Boot.")
                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT")));
    }
}
