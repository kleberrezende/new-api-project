package br.com.virtualab.newproject.controller.sistemainfo;

import br.com.virtualab.newproject.type.UserRoleType;
import br.com.virtualab.newproject.util.SistemaInfo;
import br.com.virtualab.newproject.viewmodel.mensagem.MensagemVM;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/" + SistemaInfo.PATH_VERSAO_API + UserRoleType.PATH_ANONYMOUS + "/sistema/info")
public class SistemaInfoController {

    @RequestMapping(method = RequestMethod.GET, value = "/sistema")
    public ResponseEntity getSistemaInfo() {
        return ResponseEntity.ok(new SistemaInfo());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/compararVersao")
    public ResponseEntity compararVersao(@RequestParam String versao) {
        SistemaInfo sistemaInfo = new SistemaInfo();
        if (sistemaInfo.getVersao().equals(versao)) {
            return ResponseEntity.ok(new MensagemVM("Versões idênticas."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensagemVM("Versão do Cliente é diferente da versão da API."));
        }
    }

}
