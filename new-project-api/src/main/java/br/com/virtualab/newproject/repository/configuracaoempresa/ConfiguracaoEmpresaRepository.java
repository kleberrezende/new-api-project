package br.com.virtualab.newproject.repository.configuracaoempresa;

import br.com.virtualab.newproject.model.configuracaoempresa.ConfiguracaoEmpresa;
import br.com.virtualab.newproject.repository.Abstract.AbstractRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ConfiguracaoEmpresaRepository extends AbstractRepository<ConfiguracaoEmpresa, Long> {

    @Transactional
    public void salvar(ConfiguracaoEmpresa configuracaoEmpresa) {
        if (configuracaoEmpresa.getId() == null) {
            configuracaoEmpresa.setId(1L);
            getEntityManager().persist(configuracaoEmpresa);
        } else {
            getEntityManager().merge(configuracaoEmpresa);
        }
        getEntityManager().flush();
    }

}
