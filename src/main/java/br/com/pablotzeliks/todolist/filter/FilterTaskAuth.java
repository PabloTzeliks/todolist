package br.com.pablotzeliks.todolist.filter;

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

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var servlePath = request.getServletPath();

        if (!servlePath.equals("/tasks/create")) {

            filterChain.doFilter(request, response);
            return;
        }

        var auth = request.getHeader("Authorization");
        var authEncoded = auth.substring("Basic".length()).trim();
        var authDecoded = new String(Base64.getDecoder().decode(authEncoded));

        String[] credentials = authDecoded.split(":");

        var username = credentials[0];
        var password = credentials[1];

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        var user = userRepository.findByUsername(username);

        if (user == null) {

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized User");
        } else {

            var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

            if (!passwordVerify.verified) {

                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized User");
                return;
            }

            filterChain.doFilter(request, response);
        }
    }
}
