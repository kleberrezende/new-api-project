package br.com.virtualab.newproject.service.autoridade;

import br.com.virtualab.newproject.exception.BusinessException;
import br.com.virtualab.newproject.model.autoridade.Autoridade;
import br.com.virtualab.newproject.model.autoridade.QAutoridade;
import br.com.virtualab.newproject.repository.autoridade.AutoridadeRepository;
import br.com.virtualab.newproject.util.StringUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutoridadeService {

    @Autowired
    private AutoridadeRepository autoridadeRepository;

    @Transactional
    public Autoridade salvar(Autoridade autoridade) {
        autoridadeRepository.save(autoridade);
        return autoridade;
    }

    public Autoridade buscarPorId(String id) {
        return autoridadeRepository.findById(id);
    }

    public List<Autoridade> buscar() {
        QAutoridade qAutoridade = QAutoridade.autoridade;
        BooleanBuilder where = new BooleanBuilder();

        OrderSpecifier<String> sortOrder = qAutoridade.descricao.asc();
        return autoridadeRepository.buscar(where, sortOrder);
    }

    public List<Autoridade> autoComplete(String criterio) {
        StringUtils.trim(criterio);
        QAutoridade qAutoridade = QAutoridade.autoridade;
        BooleanBuilder where = new BooleanBuilder();

        if (!StringUtils.isStringEmpty(criterio) && criterio.length() >= 3) {
            where.and(qAutoridade.descricao.startsWith(criterio)
                    .or(qAutoridade.nome.startsWith(criterio)));
        }

        if (where.hasValue()) {
            OrderSpecifier<String> sortOrder = qAutoridade.descricao.asc();
            return autoridadeRepository.buscar(where, sortOrder);
        } else {
            throw new BusinessException("Filtro para auto complete de Autoridade insuficiênte. Mínimo 3 caracteres.");
        }
    }

}
