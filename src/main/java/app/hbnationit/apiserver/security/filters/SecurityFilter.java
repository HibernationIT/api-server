package app.hbnationit.apiserver.security.filters;

import app.hbnationit.apiserver.security.services.AccountDetailsService;
import app.hbnationit.apiserver.security.utils.JwtUtil;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    protected final Log logger = LogFactory.getLog(getClass());
    private final JwtUtil jwtUtil;
    private final AccountDetailsService detailsService;

    public SecurityFilter(JwtUtil jwtUtil, AccountDetailsService detailsService) {
        this.jwtUtil = jwtUtil;
        this.detailsService = detailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        try {
            if (!authorization.startsWith("Bearer"))
                throw new AuthorizationServiceException("Not supported Authorization");
            String token = authorization.split(" ")[1].trim();
            Claims claims = jwtUtil.parsingJwt(token);
            UserDetails details = detailsService.loadUserByUsername(claims.getSubject());
            Authentication authentication = new UsernamePasswordAuthenticationToken(details, "", Collections.emptyList());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterSuccessLogging(request);
        } catch(Exception exception) {
            filterExceptionLogging(request, exception);
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private void filterSuccessLogging(HttpServletRequest request) {
        logger.info("[" + request.getRemoteAddr() + "] credentials completed");
    }

    private void filterExceptionLogging(HttpServletRequest request, Exception exception) {
        Class<?> clazz = exception.getClass();
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(request.getRemoteAddr()).append("] ");
        sb.append("<").append(clazz.getSimpleName()).append("> ");
        if (clazz.equals(ExpiredJwtException.class)) {
            sb.append("Jwt is expired.");
        } else if (clazz.equals(UnsupportedJwtException.class)) {
            sb.append("Not supporting token.");
        } else if (clazz.equals(MalformedJwtException.class)) {
            sb.append("Invalid type token.");
        } else if (clazz.equals(SignatureException.class)) {
            sb.append("Invalid signature token.");
        } else if (clazz.equals(IllegalArgumentException.class)) {
            sb.append("Invalid argument token.");
        } else if (clazz.equals(AuthorizationServiceException.class)) {
            sb.append("Not supported Authorization.");
        } else {
            return;
        }
        logger.info(sb.toString());
    }
}
