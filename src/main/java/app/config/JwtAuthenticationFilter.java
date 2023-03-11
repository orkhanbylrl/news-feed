package app.config;

import app.config.jwt.JwtService;
import app.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;


    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest rq,
            @NotNull HttpServletResponse rs,
            @NotNull FilterChain filterChain) throws ServletException, IOException {


        final String authHeader = (String) UserService.forToken.get("Authorization");
        Authentication auth = (Authentication) UserService.forToken.get("auth");

        final String jwt;
        final String userEmail;


        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(rq, rs);
            return;
        }

        if(auth != null){
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);

        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() != null) {
            UserDetails userDetails = this.userService.loadUserByUsername(userEmail);
            if(jwtService.isValid(jwt, userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(rq)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(rq, rs);
    }
}
