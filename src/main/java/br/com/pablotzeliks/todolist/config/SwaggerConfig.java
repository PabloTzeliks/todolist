package br.com.pablotzeliks.todolist.config;

import br.com.pablotzeliks.todolist.exception.dto.ErrorResponseDTO;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Schema errorSchema = new Schema<ErrorResponseDTO>()
                .$ref("ErrorResponseDTO");

        return new OpenAPI()
                .info(new Info()
                        .title("TodoList API")
                        .version("2.0.0")
                        .description("API Profissional de Gerenciamento de Tarefas")
                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT")))
                .components(new Components()
                        .addSchemas("ErrorResponseDTO", errorSchema));
    }

    @Bean
    public OpenApiCustomizer globalHeaderOpenApiCustomiser() {
        return openApi -> {
            openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
                ApiResponses responses = operation.getResponses();

                // Adiciona 400 Bad Request
                responses.addApiResponse("400", createApiResponse("Erro de Validação / Regra de Negócio"));

                // Adiciona 401 Unauthorized em tudo
                responses.addApiResponse("401", createApiResponse("Não Autorizado"));

                // Adiciona 500 Internal Error
                responses.addApiResponse("500", createApiResponse("Erro Interno do Servidor"));
            }));
        };
    }

    // Método auxiliar para não repetir código
    private ApiResponse createApiResponse(String description) {
        return new ApiResponse()
                .description(description)
                .content(new Content()
                        .addMediaType("application/json",
                                new MediaType().schema(new Schema<ErrorResponseDTO>().$ref("#/components/schemas/ErrorResponseDTO"))));
    }
}