package br.com.virtualab.newproject.controller.autoridade;

import br.com.virtualab.newproject.mapper.autoridade.AutoridadeMapper;
import br.com.virtualab.newproject.service.autoridade.AutoridadeService;
import br.com.virtualab.newproject.type.UserRoleType;
import br.com.virtualab.newproject.util.SistemaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/" + SistemaInfo.PATH_VERSAO_API)
public class AutoridadeController {

    private final String PATH_LOCAL = "/autoridade";

    @Autowired
    private AutoridadeService autoridadeService;

    @RequestMapping(method = RequestMethod.GET, value = UserRoleType.PATH_ADMIN + PATH_LOCAL + "/autoComplete")
    public ResponseEntity autoComplete(@RequestParam String criterio) {
        return ResponseEntity.ok(AutoridadeMapper.toVM(autoridadeService.autoComplete(criterio)));
    }

    @RequestMapping(method = RequestMethod.GET, value = UserRoleType.PATH_USER + PATH_LOCAL + "/buscar")
    public ResponseEntity buscar() {
        return ResponseEntity.ok(AutoridadeMapper.toVM(autoridadeService.buscar()));
    }

}
