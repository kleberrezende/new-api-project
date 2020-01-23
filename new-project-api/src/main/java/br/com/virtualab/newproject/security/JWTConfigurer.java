package br.com.virtualab.newproject.security;

import br.com.virtualab.newproject.service.securitytoken.SecurityTokenService;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String START_TOKEN = "Bearer ";

    private TokenProvider tokenProvider;

    private SecurityTokenService securityTokenService;

    public JWTConfigurer(TokenProvider tokenProvider, SecurityTokenService securityTokenService) {
        this.tokenProvider = tokenProvider;
        this.securityTokenService = securityTokenService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        JWTFilter customFilter = new JWTFilter(tokenProvider, securityTokenService);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
