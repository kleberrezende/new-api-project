package br.com.virtualab.newproject.service.securitytoken;

import br.com.virtualab.newproject.exception.BusinessException;
import br.com.virtualab.newproject.model.securitytoken.SecurityToken;
import br.com.virtualab.newproject.model.usuario.Usuario;
import br.com.virtualab.newproject.repository.securitytoken.SecurityTokenRepository;
import br.com.virtualab.newproject.service.usuario.UsuarioAutenticacaoService;
import br.com.virtualab.newproject.util.StringUtils;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class SecurityTokenService {

    @Autowired
    private SecurityTokenRepository securityTokenRepository;

    @Autowired
    private UsuarioAutenticacaoService usuarioAutenticaoService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private UsuarioAutenticacaoService usuarioAutenticacaoService;

    @Transactional
    public void salvar(String token, String autenticacao) {
        Assert.isTrue(!StringUtils.isStringEmpty(token), "Problema na autenticação, campo Token não pode ser vazio.");
        Assert.isTrue(!StringUtils.isStringEmpty(autenticacao), "Problema na autenticação, campo Autenticação não pode ser vazio.");

        String idRequest = getIdRequest();
        Usuario usuario = usuarioAutenticaoService.buscarPorAutenticacao(autenticacao);
        List<SecurityToken> securityTokens = securityTokenRepository.buscarPorUsuarioOuIdRequest(usuario, idRequest);
        for (SecurityToken securityToken : securityTokens) {
            securityTokenRepository.remove(securityToken);
        }

        Optional optional = securityTokenRepository.buscarPorToken(token);
        if (optional.isPresent()) {
            securityTokenRepository.remove((SecurityToken) optional.get());
            throw new BusinessException("Problema na autenticação, não foi possível gerar um novo token.");
        }

        SecurityToken securityToken = new SecurityToken(token, idRequest, usuario);
        securityTokenRepository.save(securityToken);
    }

    @Transactional(readOnly = true)
    public boolean isTokenValido(String token) {
        Optional optional = securityTokenRepository.buscarPorToken(token);
        if (optional.isPresent()) {
            SecurityToken securityToken = (SecurityToken) optional.get();
            if (!securityToken.getUsuario().equals(usuarioAutenticacaoService.getUsuarioPeloContexto())
                    || !securityToken.getIdRequest().equals(getIdRequest())) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private String getIdRequest() {
        String userAgent = httpServletRequest.getHeader("User-Agent");

        String ip = "IP: ";
        if (httpServletRequest.getHeader("X-Forwarded-For") != null) {
            ip = ip + httpServletRequest.getHeader("X-Forwarded-For") + ", ";
        }
        ip = ip + httpServletRequest.getRemoteAddr();

        String host = "HOST: " + httpServletRequest.getRemoteHost();

        return userAgent + " " + ip + " " + host;
    }

}
