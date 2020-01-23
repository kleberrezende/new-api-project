package br.com.virtualab.newproject.security;

import br.com.virtualab.newproject.model.usuario.Usuario;
import br.com.virtualab.newproject.repository.usuario.UsuarioRepository;
import br.com.virtualab.newproject.service.usuario.UsuarioService;
import br.com.virtualab.newproject.util.Log;
import br.com.virtualab.newproject.util.StringUtils;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    public DomainUserDetailsService(UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String autenticacao) {
        Log.debug(getClass(), "Authenticating {}", autenticacao);
        String lowercaseAutenticacao = autenticacao.toLowerCase(Locale.ENGLISH);
        Optional<Usuario> userFromDatabase = usuarioRepository.buscarPorAutenticacao(lowercaseAutenticacao);
        return userFromDatabase.map(usuario -> {
            if (!usuario.isAtivado()) {
                loginFail("Usuário '" + lowercaseAutenticacao + "' não está ativado. Verifique em seu e-mail o link para ativar seu cadastro.");
            }

            if (!usuario.isAtivo()) {
                loginFail("Usuário '" + lowercaseAutenticacao + "' não está ativo.");
            }

            if (usuario.isBloqueadoTentativaLogin()) {
                loginFail("Usuário '" + lowercaseAutenticacao + "' está bloqueado por ter feito mais de " + SecurityProperties.NR_TENTATIVA_LOGIN + " tentativas de login errada."
                        + StringUtils.breakLineHTML()
                        + "Tente novamente após " + usuario.getDataTentativaLoginEm() + ".");
            }

            if (usuario.getAutoridades().isEmpty()) {
                loginFail("Usuário '" + lowercaseAutenticacao + "' sem permissão definida.");
            }

            List<GrantedAuthority> grantedAuthorities = usuario.getAutoridades().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getNome()))
                    .collect(Collectors.toList());

            return new org.springframework.security.core.userdetails.User(lowercaseAutenticacao,
                    usuario.getPassword(),
                    grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException("Usuário '" + lowercaseAutenticacao + "' não encontrado no sistema."));
    }

    private void loginFail(String msg) {
        throw new AuthenticationServiceException(msg + StringUtils.breakLineHTML() + "Para suporte entre em contato com o Administrador do sistema.");
    }

}
