package br.com.pablotzeliks.todolist.security;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.pablotzeliks.todolist.user.exception.UserNotAuthorizedException;
import br.com.pablotzeliks.todolist.user.repository.IUserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Base64;

/**
 * Filtro de autenticação para endpoints de tarefas.
 * <p>
 * Este filtro intercepta todas as requisições direcionadas aos endpoints {@code /tasks/}
 * e valida a autenticação do usuário através do esquema Basic Auth. Caso a autenticação
 * seja bem-sucedida, o identificador do usuário é adicionado como atributo da requisição
 * para uso posterior pelos Controllers e Services.
 * </p>
 * <p>
 * <strong>Integração com o GlobalExceptionHandler:</strong><br>
 * Utiliza injeção do {@code HandlerExceptionResolver} para delegar o tratamento de
 * exceções de autenticação ao {@link br.com.pablotzeliks.todolist.exception.GlobalExceptionHandler},
 * garantindo respostas JSON padronizadas mesmo para erros ocorridos no filtro.
 * </p>
 * <p>
 * A anotação {@code @Component} registra este filtro como um bean gerenciado pelo Spring.
 * Estende {@link OncePerRequestFilter} para garantir que o filtro seja executado apenas
 * uma vez por requisição, mesmo em casos de redirecionamento interno.
 * </p>
 *
 * @author Pablo Tzeliks
 * @version 2.0.0
 * @since 1.0.0
 * @see OncePerRequestFilter
 * @see IUserRepository
 * @see br.com.pablotzeliks.todolist.exception.GlobalExceptionHandler
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
     * Resolver de exceções do Spring MVC.
     * <p>
     * Injetado com {@code @Qualifier("handlerExceptionResolver")} para garantir
     * que exceções lançadas durante a autenticação sejam tratadas pelo
     * {@link br.com.pablotzeliks.todolist.exception.GlobalExceptionHandler},
     * retornando JSON padronizado em vez de respostas HTML de erro.
     * </p>
     */
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var servlePath = request.getServletPath();

        if (!servlePath.startsWith("/tasks/")) {

            filterChain.doFilter(request, response);
            return;
        }

        try {

            validateAuth(request);

            filterChain.doFilter(request, response);

        } catch (Exception e) {

            resolver.resolveException(request, response, null, e);
        }
    }

    private void validateAuth(HttpServletRequest request) {

        var auth = request.getHeader("Authorization");

        if (auth == null) {
            throw new UserNotAuthorizedException("Token de autenticação não fornecido.");
        }

        var authEncoded = auth.substring("Basic".length()).trim();
        var authDecoded = new String(Base64.getDecoder().decode(authEncoded));
        String[] credentials = authDecoded.split(":");

        var username = credentials[0];
        var password = credentials[1];

        var user = userRepository.findByUsername(username);

        if (user == null) {

            throw new UserNotAuthorizedException("Usuário ou senha inválidos.");
        }

        var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

        if (!passwordVerify.verified) {
            throw new UserNotAuthorizedException("Usuário ou senha inválidos.");
        }

        request.setAttribute("userId", user.getId());
    }
}
