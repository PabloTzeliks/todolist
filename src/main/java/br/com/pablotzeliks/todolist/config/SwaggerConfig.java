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

/**
 * Configuração do Swagger/OpenAPI para documentação interativa da API.
 * <p>
 * Esta classe configura a geração automática de documentação viva da API através
 * do SpringDoc OpenAPI, disponível via Swagger UI. A documentação pode ser acessada
 * em {@code http://localhost:8080/swagger-ui/index.html} durante o desenvolvimento.
 * </p>
 * <p>
 * <strong>Funcionalidades implementadas:</strong>
 * <ul>
 *   <li>Metadados da API (título, versão, descrição, licença)</li>
 *   <li>Esquemas de resposta de erro padronizados (ErrorResponseDTO)</li>
 *   <li>Respostas globais (400, 401, 500) aplicadas a todos os endpoints</li>
 * </ul>
 * </p>
 *
 * @author Pablo Tzeliks
 * @version 2.0.0
 * @since 1.0.0
 * @see ErrorResponseDTO
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configura os metadados da API no Swagger.
     * <p>
     * Define informações gerais como título, versão, descrição e licença,
     * além de registrar o esquema de erro padrão para uso na documentação.
     * </p>
     *
     * @return Objeto OpenAPI configurado
     */
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

    /**
     * Adiciona respostas HTTP globais a todos os endpoints.
     * <p>
     * Este customizador injeta automaticamente as respostas 400 (Bad Request),
     * 401 (Unauthorized) e 500 (Internal Server Error) em todos os endpoints,
     * evitando a necessidade de documentar essas respostas manualmente em cada
     * método do Controller.
     * </p>
     *
     * @return Customizador que adiciona respostas globais
     */
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

    /**
     * Método auxiliar para criação de respostas de erro padronizadas.
     * <p>
     * Reduz duplicação de código ao criar objetos ApiResponse com o schema
     * de ErrorResponseDTO configurado.
     * </p>
     *
     * @param description Descrição da resposta de erro
     * @return Objeto ApiResponse configurado
     */
    private ApiResponse createApiResponse(String description) {
        return new ApiResponse()
                .description(description)
                .content(new Content()
                        .addMediaType("application/json",
                                new MediaType().schema(new Schema<ErrorResponseDTO>().$ref("#/components/schemas/ErrorResponseDTO"))));
    }
}