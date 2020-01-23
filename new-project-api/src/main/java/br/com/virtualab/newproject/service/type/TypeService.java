package br.com.virtualab.newproject.service.type;

import br.com.virtualab.newproject.type.UserRoleType;
import br.com.virtualab.newproject.viewmodel.type.TypeItemVM;
import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TypeService {

    public List<TypeItemVM> getItemsUserRoleType() {
        List<TypeItemVM> items = new LinkedList<TypeItemVM>();
        for (UserRoleType enumType : UserRoleType.values()) {
            if (enumType != UserRoleType.ROLE_ANONYMOUS) {
                items.add(new TypeItemVM(enumType.name(), enumType.getDescricao()));
            }
        }
        return items;
    }

}
