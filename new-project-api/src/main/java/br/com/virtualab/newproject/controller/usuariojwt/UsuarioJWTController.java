package br.com.virtualab.newproject.controller.usuariojwt;

import br.com.virtualab.newproject.security.JWTConfigurer;
import br.com.virtualab.newproject.security.TokenProvider;
import br.com.virtualab.newproject.service.securitytoken.SecurityTokenService;
import br.com.virtualab.newproject.service.usuario.UsuarioAutenticacaoService;
import br.com.virtualab.newproject.type.UserRoleType;
import br.com.virtualab.newproject.util.EncryptBasic;
import br.com.virtualab.newproject.util.SistemaInfo;
import br.com.virtualab.newproject.viewmodel.login.JWTTokenVM;
import br.com.virtualab.newproject.viewmodel.login.LoginVM;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/" + SistemaInfo.PATH_VERSAO_API + UserRoleType.PATH_ANONYMOUS + "/usuario/jwt")
public class UsuarioJWTController {

    @Autowired
    private UsuarioAutenticacaoService usuarioAutenticacaoService;

    @Autowired
    private SecurityTokenService securityTokenService;

    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    public UsuarioJWTController(TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/authenticate")
    public ResponseEntity<JWTTokenVM> authorize(@Valid @RequestBody LoginVM loginVM) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginVM.getAutenticacao(), loginVM.getPassword());

        Authentication authentication;
        try {
            authentication = this.authenticationManager.authenticate(authenticationToken);
            usuarioAutenticacaoService.autenticacaoOK(loginVM.getAutenticacao());
        } catch (BadCredentialsException ex) {
            String msg = usuarioAutenticacaoService.autenticacaoBadCredentials(loginVM.getAutenticacao());
            throw new BadCredentialsException(msg);
        } catch (Exception ex) {
            throw ex;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, loginVM.isRememberMe());
        securityTokenService.salvar(EncryptBasic.decrypt(jwt), loginVM.getAutenticacao());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTConfigurer.AUTHORIZATION_HEADER, JWTConfigurer.START_TOKEN + jwt);
        return new ResponseEntity<>(new JWTTokenVM(jwt), httpHeaders, HttpStatus.OK);
    }

}
