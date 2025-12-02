package br.com.pablotzeliks.todolist.security;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.pablotzeliks.todolist.user.repository.IUserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

/**
 * Filtro de autenticação para endpoints de tarefas.
 * <p>
 * Este filtro intercepta todas as requisições direcionadas aos endpoints {@code /tasks/}
 * e valida a autenticação do usuário através do esquema Basic Auth. Caso a autenticação
 * seja bem-sucedida, o identificador do usuário é adicionado como atributo da requisição.
 * </p>
 * <p>
 * A anotação {@code @Component} registra este filtro como um bean gerenciado pelo Spring.
 * Estende {@link OncePerRequestFilter} para garantir que o filtro seja executado apenas
 * uma vez por requisição, mesmo em casos de redirecionamento interno.
 * </p>
 *
 * @author Pablo Tzeliks
 * @version 1.0.0
 * @since 1.0.0
 * @see OncePerRequestFilter
 * @see IUserRepository
 */
@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    /**
     * Repositório para busca de usuários durante a autenticação.
     * Injetado automaticamente pelo Spring através de {@code @Autowired}.
     */
    @Autowired
    private IUserRepository userRepository;

    /**
     * Executa a lógica de autenticação para requisições de tarefas.
     * <p>
     * O processo de autenticação segue os seguintes passos:
     * </p>
     * <ol>
     *   <li>Verifica se a URL começa com {@code /tasks/}. Caso contrário, permite a requisição.</li>
     *   <li>Extrai e decodifica as credenciais do header {@code Authorization} (Basic Auth).</li>
     *   <li>Busca o usuário pelo username no banco de dados.</li>
     *   <li>Verifica a senha utilizando BCrypt.</li>
     *   <li>Em caso de sucesso, adiciona o userId como atributo da requisição.</li>
     * </ol>
     *
     * @param request objeto da requisição HTTP
     * @param response objeto da resposta HTTP
     * @param filterChain cadeia de filtros para continuar o processamento
     * @throws ServletException se ocorrer um erro durante o processamento do servlet
     * @throws IOException se ocorrer um erro de entrada/saída
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var servlePath = request.getServletPath();

        if (!servlePath.startsWith("/tasks/")) {

            filterChain.doFilter(request, response);
            return;
        }

        var auth = request.getHeader("Authorization");
        var authEncoded = auth.substring("Basic".length()).trim();
        var authDecoded = new String(Base64.getDecoder().decode(authEncoded));

        String[] credentials = authDecoded.split(":");

        var username = credentials[0];
        var password = credentials[1];

        var user = userRepository.findByUsername(username);

        if (user == null) {

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized User");
        } else {

            var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

            if (!passwordVerify.verified) {

                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized User");
                return;
            }

            request.setAttribute("userId", user.getId());

            filterChain.doFilter(request, response);
        }
    }
}
