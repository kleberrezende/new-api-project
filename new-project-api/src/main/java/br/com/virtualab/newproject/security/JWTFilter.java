package br.com.virtualab.newproject.security;

import br.com.virtualab.newproject.service.securitytoken.SecurityTokenService;
import br.com.virtualab.newproject.util.EncryptBasic;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Filters incoming requests and installs a Spring Security principal if a
 * header corresponding to a valid user is found.
 */
public class JWTFilter extends GenericFilterBean {

    private TokenProvider tokenProvider;

    private SecurityTokenService securityTokenService;

    public JWTFilter(TokenProvider tokenProvider, SecurityTokenService securityTokenService) {
        this.tokenProvider = tokenProvider;
        this.securityTokenService = securityTokenService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(httpServletRequest);
        if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)) {
            Authentication authentication = this.tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            if (!securityTokenService.isTokenValido(jwt)) {
                SecurityContextHolder.getContext().setAuthentication(null);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JWTConfigurer.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JWTConfigurer.START_TOKEN)) {
            return EncryptBasic.decrypt(bearerToken.substring(JWTConfigurer.START_TOKEN.length(), bearerToken.length()));
        }
        return null;
    }

}
