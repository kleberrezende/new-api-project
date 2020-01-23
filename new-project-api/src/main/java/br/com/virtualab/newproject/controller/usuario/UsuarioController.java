package br.com.virtualab.newproject.controller.usuario;

import br.com.virtualab.newproject.mapper.usuario.UsuarioMapper;
import br.com.virtualab.newproject.service.usuario.UsuarioAutenticacaoService;
import br.com.virtualab.newproject.service.usuario.UsuarioService;
import br.com.virtualab.newproject.type.UserRoleType;
import br.com.virtualab.newproject.util.SistemaInfo;
import br.com.virtualab.newproject.viewmodel.mensagem.MensagemVM;
import br.com.virtualab.newproject.viewmodel.usuario.AlterarSenhaVM;
import br.com.virtualab.newproject.viewmodel.usuario.AtivarUsuarioVM;
import br.com.virtualab.newproject.viewmodel.usuario.EnviarResetarUsuarioVM;
import br.com.virtualab.newproject.viewmodel.usuario.ResetarUsuarioVM;
import br.com.virtualab.newproject.viewmodel.usuario.UsuarioVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/" + SistemaInfo.PATH_VERSAO_API)
public class UsuarioController {

    private final String PATH_LOCAL = "/usuario";

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioAutenticacaoService usuarioAutenticacaoService;

    @RequestMapping(method = RequestMethod.GET, value = UserRoleType.PATH_USER + PATH_LOCAL + "/buscarPeloToken")
    public ResponseEntity buscarPeloToken() {
        return ResponseEntity.ok(UsuarioMapper.toVM(usuarioAutenticacaoService.getUsuarioPeloContexto()));
    }

    @RequestMapping(method = RequestMethod.PUT, value = UserRoleType.PATH_USER + PATH_LOCAL + "/alterarSenha")
    public ResponseEntity alterarSenha(@RequestBody AlterarSenhaVM alterarSenhaVM) {
        usuarioService.alterarSenha(alterarSenhaVM, true);
        return ResponseEntity.ok(new MensagemVM("Senha alterada com sucesso."));
    }

    @RequestMapping(method = RequestMethod.PUT, value = UserRoleType.PATH_ANONYMOUS + PATH_LOCAL + "/ativarUsuario")
    public ResponseEntity ativarUsuario(@RequestBody AtivarUsuarioVM ativarUsuarioVM) {
        usuarioService.ativarUsuario(ativarUsuarioVM);
        return ResponseEntity.ok(new MensagemVM("Sua conta foi ativada com sucesso."));
    }

    @RequestMapping(method = RequestMethod.PUT, value = UserRoleType.PATH_ANONYMOUS + PATH_LOCAL + "/enviarResetarUsuario")
    public ResponseEntity enviarResetarUsuario(@RequestBody EnviarResetarUsuarioVM enviarResetarUsuarioVM) {
        usuarioService.enviarResetarUsuario(enviarResetarUsuarioVM);
        return ResponseEntity.ok(new MensagemVM("Caso a credencial seja válida uma Chave para resetar usuário foi enviada ao e-mail cadastrado."));
    }

    @RequestMapping(method = RequestMethod.PUT, value = UserRoleType.PATH_ANONYMOUS + PATH_LOCAL + "/resetarUsuario")
    public ResponseEntity resetarUsuario(@RequestBody ResetarUsuarioVM resetarUsuarioVM) {
        usuarioService.resetarUsuario(resetarUsuarioVM);
        return ResponseEntity.ok(new MensagemVM("Sua conta foi resetada com sucesso."));
    }

    @RequestMapping(method = RequestMethod.POST, value = UserRoleType.PATH_ANONYMOUS + PATH_LOCAL + "/novo")
    public ResponseEntity novo(@RequestBody UsuarioVM usuarioVM) {
        usuarioService.novo(UsuarioMapper.fromVM(usuarioVM), usuarioVM.getUri(), usuarioVM.getTipoAplicativo());
        return ResponseEntity.ok(new MensagemVM("Novo usuário cadastrado. Verifique seu e-mail para ativar seu cadastro."));
    }

}
