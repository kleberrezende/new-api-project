package br.com.virtualab.newproject.controller.configuracaoempresa;

import br.com.virtualab.newproject.mapper.configuracaoempresa.ConfiguracaoEmpresaMapper;
import br.com.virtualab.newproject.service.configuracaoempresa.ConfiguracaoEmpresaService;
import br.com.virtualab.newproject.type.UserRoleType;
import br.com.virtualab.newproject.util.SistemaInfo;
import br.com.virtualab.newproject.viewmodel.configuracaoempresa.ConfiguracaoEmpresaVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/" + SistemaInfo.PATH_VERSAO_API)
public class ConfiguracaoEmpresaController {

    private final String PATH_LOCAL = "/configuracao/empresa";

    @Autowired
    private ConfiguracaoEmpresaService configuracaoEmpresaService;

    @RequestMapping(method = RequestMethod.POST, value = UserRoleType.PATH_ADMIN + PATH_LOCAL + "/salvar")
    public ResponseEntity salvar(@RequestBody ConfiguracaoEmpresaVM configuracaoEmpresaVM) {
        return ResponseEntity.ok(ConfiguracaoEmpresaMapper.toVM(configuracaoEmpresaService.salvar(ConfiguracaoEmpresaMapper.fromVM(configuracaoEmpresaVM))));
    }

    @RequestMapping(method = RequestMethod.GET, value = UserRoleType.PATH_USER + PATH_LOCAL + "/buscar")
    public ResponseEntity buscar() {
        return ResponseEntity.ok(ConfiguracaoEmpresaMapper.toVM(configuracaoEmpresaService.buscar()));
    }

}
