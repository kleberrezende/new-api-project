package br.com.virtualab.newproject.service.configuracaoempresa;

import br.com.virtualab.newproject.model.configuracaoempresa.ConfiguracaoEmpresa;
import br.com.virtualab.newproject.repository.configuracaoempresa.ConfiguracaoEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConfiguracaoEmpresaService {

    @Autowired
    private ConfiguracaoEmpresaRepository configuracaoEmpresaRepository;

    @Transactional
    public ConfiguracaoEmpresa salvar(ConfiguracaoEmpresa configuracaoEmpresa) {
        configuracaoEmpresaRepository.salvar(configuracaoEmpresa);
        return configuracaoEmpresa;
    }

    public ConfiguracaoEmpresa buscar() {
        ConfiguracaoEmpresa configuracaoEmpresa = configuracaoEmpresaRepository.findById(1L);
        return (configuracaoEmpresa != null ? configuracaoEmpresa : new ConfiguracaoEmpresa());
    }

}
