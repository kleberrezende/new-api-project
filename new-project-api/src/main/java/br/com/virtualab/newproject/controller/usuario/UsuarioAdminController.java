package br.com.virtualab.newproject.controller.usuario;

import br.com.virtualab.newproject.mapper.usuario.UsuarioMapper;
import br.com.virtualab.newproject.service.usuario.UsuarioService;
import br.com.virtualab.newproject.type.UserRoleType;
import br.com.virtualab.newproject.util.SistemaInfo;
import br.com.virtualab.newproject.viewmodel.mensagem.MensagemVM;
import br.com.virtualab.newproject.viewmodel.usuario.EnviarAtivarUsuarioVM;
import br.com.virtualab.newproject.viewmodel.usuario.UsuarioVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/" + SistemaInfo.PATH_VERSAO_API + UserRoleType.PATH_ADMIN + "/usuario")
public class UsuarioAdminController {

    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping(method = RequestMethod.POST, value = "/salvar")
    public ResponseEntity salvar(@RequestBody UsuarioVM usuarioVM) {
        return ResponseEntity.ok(UsuarioMapper.toVM(usuarioService.salvar(UsuarioMapper.fromVM(usuarioVM),
                usuarioVM.getUri(), usuarioVM.getTipoAplicativo())));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/buscar")
    public ResponseEntity buscarUsuarios(@RequestParam String login,
            @RequestParam String nome,
            @RequestParam String email) {
        return ResponseEntity.ok(UsuarioMapper.toVM(usuarioService.buscarUsuarios(login, nome, email)));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/buscarPorId")
    public ResponseEntity buscarPorId(@RequestParam Long id) {
        return ResponseEntity.ok(UsuarioMapper.toVM(usuarioService.buscarUsuarioPorId(id)));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/enviarAtivarUsuario")
    public ResponseEntity enviarAtivarUsuario(@RequestBody EnviarAtivarUsuarioVM enviarAtivarUsuarioVM) {
        usuarioService.enviarAtivarUsuario(enviarAtivarUsuarioVM);
        return ResponseEntity.ok(new MensagemVM("Chave para ativar o usuário foi enviada ao e-mail cadastrado."));
    }

}
