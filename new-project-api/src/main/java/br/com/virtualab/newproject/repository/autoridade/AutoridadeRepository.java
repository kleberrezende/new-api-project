package br.com.virtualab.newproject.repository.autoridade;

import br.com.virtualab.newproject.model.autoridade.Autoridade;
import br.com.virtualab.newproject.model.autoridade.QAutoridade;
import br.com.virtualab.newproject.repository.Abstract.AbstractRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class AutoridadeRepository extends AbstractRepository<Autoridade, String> {

    public List<Autoridade> buscar(BooleanBuilder where, OrderSpecifier<String> sortOrder) {
        JPAQuery<?> query = createJPAQuery();
        QAutoridade qAutoridade = QAutoridade.autoridade;
        List<Autoridade> autoridades = query.select(qAutoridade)
                .from(qAutoridade)
                .where(where)
                .orderBy(sortOrder)
                .fetch();
        return autoridades;
    }

}
