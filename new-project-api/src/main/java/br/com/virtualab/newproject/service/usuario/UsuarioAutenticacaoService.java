package br.com.virtualab.newproject.service.usuario;

import br.com.virtualab.newproject.model.usuario.Usuario;
import br.com.virtualab.newproject.repository.usuario.UsuarioRepository;
import br.com.virtualab.newproject.security.SecurityProperties;
import br.com.virtualab.newproject.util.DateTimeUtils;
import br.com.virtualab.newproject.util.StringUtils;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioAutenticacaoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario buscarPorAutenticacao(String autenticacao) {
        StringUtils.trim(autenticacao);
        String lowercaseAutenticacao = autenticacao.toLowerCase(Locale.ENGLISH);
        return usuarioRepository.buscarPorAutenticacao(lowercaseAutenticacao)
                .map(usuario -> {
                    usuario.getAutoridades().isEmpty();
                    return usuario;
                })
                .orElse(null);
    }

    @Transactional
    public String autenticacaoBadCredentials(String autenticacao) {
        StringBuilder msg = new StringBuilder("Login e/ou Senha inválido(s).");
        Usuario usuario = buscarPorAutenticacao(autenticacao);
        if (usuario != null) {
            usuario.setTentativaLogin(usuario.getTentativaLogin() + 1);
            usuario.setDataUltimaTentativa(DateTimeUtils.getLocalDateTimeNow());
            usuarioRepository.save(usuario);
            msg.append(StringUtils.breakLineHTML());
            msg.append("Tentativas erradas ").append(usuario.getTentativaLogin()).append(" de ").append(SecurityProperties.NR_TENTATIVA_LOGIN).append(" permitidas.");
        }
        return msg.toString();
    }

    @Transactional
    public void autenticacaoOK(String autenticacao) {
        Usuario usuario = buscarPorAutenticacao(autenticacao);
        if (usuario != null) {
            usuario.setTentativaLogin(0);
            usuario.setDataUltimoLogin(DateTimeUtils.getLocalDateTimeNow());
            usuarioRepository.save(usuario);
        }
    }

    public Usuario getUsuarioPeloContexto() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context instanceof SecurityContext) {
            Authentication authentication = context.getAuthentication();
            if (authentication instanceof Authentication) {
                return this.buscarPorAutenticacao(authentication.getName());
            }
        }
        return null;
    }

}
