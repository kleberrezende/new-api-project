package br.com.virtualab.newproject.service.usuario;

import br.com.virtualab.newproject.type.UserRoleType;
import br.com.virtualab.newproject.exception.BusinessException;
import br.com.virtualab.newproject.model.autoridade.Autoridade;
import br.com.virtualab.newproject.model.usuario.QUsuario;
import br.com.virtualab.newproject.model.usuario.Usuario;
import br.com.virtualab.newproject.repository.usuario.UsuarioRepository;
import br.com.virtualab.newproject.service.autoridade.AutoridadeService;
import br.com.virtualab.newproject.type.TipoAplicativoType;
import br.com.virtualab.newproject.util.DateTimeUtils;
import br.com.virtualab.newproject.util.StringUtils;
import br.com.virtualab.newproject.viewmodel.usuario.AlterarSenhaVM;
import br.com.virtualab.newproject.viewmodel.usuario.AtivarUsuarioVM;
import br.com.virtualab.newproject.viewmodel.usuario.EnviarAtivarUsuarioVM;
import br.com.virtualab.newproject.viewmodel.usuario.EnviarResetarUsuarioVM;
import br.com.virtualab.newproject.viewmodel.usuario.ResetarUsuarioVM;
import com.querydsl.core.BooleanBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class UsuarioService {

    private final String ATIVACAO = "ATIVACAO";
    private final String RESET = "RESET";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AutoridadeService autoridadeService;

    @Autowired
    private UsuarioAutenticacaoService usuarioAutenticacaoService;

    @Autowired
    private UsuarioDetalhesService usuarioDetalhesService;

    @Transactional
    public Usuario salvar(Usuario usuario, String uri, TipoAplicativoType tipoAplicativo) {
        toLowerCase(usuario);
        validarUsuario(usuario);
        boolean novoUsuario = usuario.getId() == null ? true : false;
        usuarioRepository.save(usuario);
        if (novoUsuario) {
            usuarioDetalhesService.sendEmailAtivarUsuario(usuario, uri, tipoAplicativo);
        }
        usuario = usuarioRepository.findById(usuario.getId());
        usuario.getAutoridades().size();
        return usuario;
    }

    @Transactional
    public void novo(Usuario usuario, String uri, TipoAplicativoType tipoAplicativo) {
        usuario.setId(null);
        usuario.setAtivo(true);
        usuario.setAutoridades(new ArrayList<>());
        usuario.getAutoridades().add(autoridadeService.buscarPorId("ROLE_USER"));
        validarLogin(usuario);
        salvar(usuario, uri, tipoAplicativo);
    }

    private void validarLogin(Usuario usuario) {
        toLowerCase(usuario);
        Optional optional = usuarioRepository.buscarPorLogin(usuario.getLogin());
        if (optional.isPresent()) {
            StringBuilder msg = new StringBuilder();
            msg.append("Login indisponível, sugestão: ")
                    .append(usuario.getLogin() + StringUtils.gerarTextoAleatorio(3))
                    .append(", ")
                    .append(usuario.getLogin() + StringUtils.gerarTextoAleatorio(5))
                    .append(", ")
                    .append(usuario.getNome().split(" ")[0].toLowerCase(Locale.ENGLISH) + usuario.getSobreNome().split(" ")[0].toLowerCase(Locale.ENGLISH))
                    .append(", ")
                    .append(usuario.getSobreNome().split(" ")[0].toLowerCase(Locale.ENGLISH));
            Assert.isTrue(false, msg.toString());
        }
    }

    private void toLowerCase(Usuario usuario) {
        usuario.setLogin(usuario.getLogin().toLowerCase(Locale.ENGLISH));
        usuario.setEmail(usuario.getEmail().toLowerCase(Locale.ENGLISH));
    }

    private void validarUsuario(Usuario usuario) {
        Assert.isTrue(StringUtils.apenasLetrasENumeros(usuario.getLogin()), "Campo Login deve conter apenas letras e números.");
        Assert.isTrue(!usuario.getLogin().equals(usuario.getEmail()), "Campo Login e e-mail não podem ser iguais.");

        Optional optional = usuarioRepository.buscarPorLogin(usuario.getLogin());
        Usuario usuarioBanco = (optional.isPresent()) ? (Usuario) optional.get() : null;
        if (usuarioBanco != null && !usuarioBanco.getId().equals(usuario.getId())) {
            Assert.isTrue(false, "Já existe um usuário com o mesmo login cadastrado.");
        }

        optional = usuarioRepository.buscarPorEmail(usuario.getEmail());
        usuarioBanco = (optional.isPresent()) ? (Usuario) optional.get() : null;
        if (usuarioBanco != null && !usuarioBanco.getId().equals(usuario.getId())) {
            Assert.isTrue(false, "Já existe um usuário com o mesmo e-mail cadastrado.");
        }
        if (usuario.getId() == null) {
            novoUsuario(usuario);
        } else {
            usuarioBanco = buscarUsuarioPorId(usuario.getId());
            usuario.setPassword(usuarioBanco.getPassword());
            validarUsuarioAdmin(usuario, usuarioBanco);
        }
        validarRolesUsuario(usuario);
    }

    private void novoUsuario(Usuario usuario) {
        String senha = StringUtils.gerarTextoAleatorio(10);
        usuario.setPassword(encoderPassword(senha));
        usuario.setAtivado(false);
        usuario.setDataCriacao(DateTimeUtils.getLocalDateTimeNow());
        usuario.setDataUltimoLogin(DateTimeUtils.getLocalDateTimeNow());
        usuario.setTentativaLogin(0);
        usuario.setDataUltimaTentativa(DateTimeUtils.getLocalDateTimeNow());
        usuario.setDataReset(DateTimeUtils.getLocalDateTimeNow());
        String chaveAtivacao = gerarChave(ATIVACAO);
        usuario.setChaveAtivacao(chaveAtivacao);
    }

    private void validarUsuarioAdmin(Usuario usuarioAlterado, Usuario usuarioBanco) {
        if (usuarioBanco.getLogin().equals("admin")) {
            Assert.isTrue(usuarioAlterado.getLogin().equals("admin"), "Login do Usuário admin não pode ser alterado.");
            Assert.isTrue(usuarioAlterado.isAtivo(), "Usuário admin deve estar marcado como ativo.");
            Autoridade autoridade = new Autoridade();
            autoridade.setNome(UserRoleType.ROLE_ADMIN.name());
            Assert.isTrue(usuarioAlterado.getAutoridades().contains(autoridade), "Usuário admin deve estar com autoridade selecionada " + UserRoleType.ROLE_ADMIN.getDescricao());
        }
    }

    private void validarRolesUsuario(Usuario usuario) {
        Autoridade autoridade = new Autoridade();
        autoridade.setNome(UserRoleType.ROLE_USER.name());
        Assert.isTrue(usuario.getAutoridades().contains(autoridade), "O Usuário deve estar com autoridade mínima de Usuário selecionada.");
    }

    private String encoderPassword(String password) {
        if (password != null && password.length() != 60) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            return passwordEncoder.encode(password);
        } else {
            return password;
        }
    }

    private boolean passowordMatches(String senha, String senhaCriptografada) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(senha, senhaCriptografada);
    }

    public List<Usuario> buscarUsuarios(String login, String nome, String email) {
        StringUtils.trim(login);
        StringUtils.trim(nome);
        StringUtils.trim(email);
        QUsuario qUsuario = QUsuario.usuario;
        BooleanBuilder where = new BooleanBuilder();
        if (!StringUtils.isStringEmpty(login)) {
            where.and(qUsuario.login.startsWith(login));
        }
        if (!StringUtils.isStringEmpty(nome)) {
            where.and(qUsuario.nome.startsWith(nome));
        }
        if (!StringUtils.isStringEmpty(email)) {
            where.and(qUsuario.email.startsWith(email));
        }
        if (where.hasValue()) {
            return usuarioRepository.buscarUsuarios(where);
        } else {
            throw new BusinessException("Filtro para busca de usuário(s) insuficiênte.");
        }
    }

    public Usuario buscarPorLogin(String login) {
        StringUtils.trim(login);
        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        return usuarioRepository.buscarPorLogin(lowercaseLogin).map(usuario -> {
            return usuario;
        }).orElseThrow(() -> new BusinessException("Usuário '" + lowercaseLogin + "' não encontrado no banco de dados."));
    }

    public Usuario buscarPorEmail(String email) {
        StringUtils.trim(email);
        String lowercaseEmail = email.toLowerCase(Locale.ENGLISH);
        return usuarioRepository.buscarPorEmail(lowercaseEmail).map(usuario -> {
            return usuario;
        }).orElseThrow(() -> new BusinessException("Usuário '" + lowercaseEmail + "' não encontrado no banco de dados."));
    }

    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    private Usuario buscarUsuarioPorChave(String tipoChave, String chave) {
        Optional optional;
        if (tipoChave.equals(RESET)) {
            optional = usuarioRepository.buscarPorChaveReset(chave);
        } else {
            optional = usuarioRepository.buscarPorChaveAtivacao(chave);
        }
        return (optional.isPresent()) ? (Usuario) optional.get() : null;
    }

    @Transactional
    public void alterarSenha(AlterarSenhaVM alterarSenhaVM, boolean enviarEmail) {
        Usuario usuario = buscarPorLogin(alterarSenhaVM.getLogin());
        Assert.isTrue(passowordMatches(alterarSenhaVM.getSenhaAtual(), usuario.getPassword()), "Senha atual incorreta.");
        Assert.isTrue(alterarSenhaVM.getNovaSenha().length() >= 8, "Nova senha deve ter no mínimo 8 caracteres.");
        Assert.isTrue(StringUtils.contemNumero(alterarSenhaVM.getNovaSenha()), "Nova senha deve ter algum número.");
        Assert.isTrue(StringUtils.contemLetra(alterarSenhaVM.getNovaSenha()), "Nova senha deve ter alguma letra.");
        Assert.isTrue(alterarSenhaVM.getNovaSenha().equals(alterarSenhaVM.getConfirmarSenha()), "Nova senha e confirmar senha devem ser idênticas.");
        usuario.setPassword(encoderPassword(alterarSenhaVM.getNovaSenha()));
        usuarioRepository.save(usuario);
        if (enviarEmail) {
            usuarioDetalhesService.sendEmailSenha(usuario, alterarSenhaVM.getNovaSenha());
        }
    }

    @Transactional
    public void enviarAtivarUsuario(EnviarAtivarUsuarioVM enviarAtivarUsuarioVM) {
        enviarResetarAtivarUsuario(ATIVACAO, enviarAtivarUsuarioVM.getAutenticacao(),
                enviarAtivarUsuarioVM.getUri(),
                enviarAtivarUsuarioVM.getTipoAplicativo());
    }

    @Transactional
    public void enviarResetarUsuario(EnviarResetarUsuarioVM enviarResetarUsuarioVM) {
        enviarResetarAtivarUsuario(RESET, enviarResetarUsuarioVM.getAutenticacao(),
                enviarResetarUsuarioVM.getUri(),
                enviarResetarUsuarioVM.getTipoAplicativo());
    }

    @Transactional
    public void enviarResetarAtivarUsuario(String tipoChave, String autenticacao, String uri, TipoAplicativoType tipoAplicativo) {
        Usuario usuario = usuarioAutenticacaoService.buscarPorAutenticacao(autenticacao);
        if (usuario != null) {
            String chave = gerarChave(tipoChave);
            if (tipoChave.equals(RESET)) {
                usuario.setChaveReset(chave);
            } else {
                usuario.setChaveAtivacao(chave);
            }
            usuarioRepository.save(usuario);
            if (tipoChave.equals(RESET)) {
                usuarioDetalhesService.sendEmailResetarUsuario(usuario, uri, tipoAplicativo);
            } else {
                usuarioDetalhesService.sendEmailAtivarUsuario(usuario, uri, tipoAplicativo);
            }
        }
    }

    @Transactional
    public void ativarUsuario(AtivarUsuarioVM ativarUsuarioVM) {
        usuarioResetarAtivar(ATIVACAO, ativarUsuarioVM.getChave(), ativarUsuarioVM.getNovaSenha(), ativarUsuarioVM.getConfirmarSenha());
    }

    @Transactional
    public void resetarUsuario(ResetarUsuarioVM resetarUsuarioVM) {
        usuarioResetarAtivar(RESET, resetarUsuarioVM.getChave(), resetarUsuarioVM.getNovaSenha(), resetarUsuarioVM.getConfirmarSenha());
    }

    @Transactional
    private void usuarioResetarAtivar(String tipoChave, String chave, String novaSenha, String confirmarSenha) {
        if (StringUtils.isStringEmpty(chave)) {
            throw new BusinessException("Chave não pode ser vazia.");
        }
        Usuario usuario = buscarUsuarioPorChave(tipoChave, chave);
        if (usuario == null) {
            throw new BusinessException("Usuário não encontrado com a chave especificada.");
        }

        String senha = StringUtils.gerarTextoAleatorio(10);
        usuarioReset(usuario, senha);
        usuarioRepository.save(usuario);
        AlterarSenhaVM alterarSenhaVM = new AlterarSenhaVM(usuario.getLogin(),
                senha, novaSenha, confirmarSenha);
        alterarSenha(alterarSenhaVM, false);
        usuarioDetalhesService.sendEmailSenha(usuario, novaSenha);
    }

    private void usuarioReset(Usuario usuario, String senha) {
        usuario.setPassword(encoderPassword(senha));
        usuario.setChaveAtivacao(null);
        usuario.setChaveReset(null);
        usuario.setDataReset(DateTimeUtils.getLocalDateTimeNow());
        usuario.setTentativaLogin(0);
        usuario.setAtivado(true);
    }

    private String gerarChave(String tipoChave) {
        String chave = StringUtils.gerarTextoAleatorio(20);
        Usuario usuario = buscarUsuarioPorChave(tipoChave, chave);
        while (usuario != null) {
            chave = StringUtils.gerarTextoAleatorio(20);
            usuario = buscarUsuarioPorChave(tipoChave, chave);
        }
        return chave;
    }

}
