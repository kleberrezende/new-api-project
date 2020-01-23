package br.com.virtualab.newproject.controller.type;

import br.com.virtualab.newproject.service.type.TypeService;
import br.com.virtualab.newproject.type.UserRoleType;
import br.com.virtualab.newproject.util.SistemaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/" + SistemaInfo.PATH_VERSAO_API + UserRoleType.PATH_USER + "/type")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @RequestMapping(method = RequestMethod.GET, value = "/userRole")
    public ResponseEntity getItemsUserRoleType() {
        return ResponseEntity.ok(typeService.getItemsUserRoleType());
    }

}
