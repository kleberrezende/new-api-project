package br.com.virtualab.newproject.controller.home;

import br.com.virtualab.newproject.util.SistemaInfo;
import br.com.virtualab.newproject.viewmodel.mensagem.MensagemVM;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/" + SistemaInfo.PATH_VERSAO_API + "/")
public class HomeController {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity home() {
        return ResponseEntity.ok(new MensagemVM("Bem vindo a New-Project."));
    }

}
