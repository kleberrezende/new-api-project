package br.com.virtualab.newproject.mapper.configuracaoempresa;

import br.com.virtualab.newproject.model.configuracaoempresa.ConfiguracaoEmpresa;
import br.com.virtualab.newproject.viewmodel.configuracaoempresa.ConfiguracaoEmpresaVM;
import java.util.List;
import java.util.stream.Collectors;

public final class ConfiguracaoEmpresaMapper {

    public static ConfiguracaoEmpresa fromVM(ConfiguracaoEmpresaVM configuracaoEmpresaVM) {
        final ConfiguracaoEmpresa configEmpresa = new ConfiguracaoEmpresa();
        configEmpresa.setId(configuracaoEmpresaVM.getId());
        configEmpresa.setTipoPessoa(configuracaoEmpresaVM.getTipoPessoa());
        configEmpresa.setNomeRazao(configuracaoEmpresaVM.getNomeRazao());
        configEmpresa.setFantasia(configuracaoEmpresaVM.getFantasia());
        configEmpresa.setFantasiaCurta(configuracaoEmpresaVM.getFantasiaCurta());
        configEmpresa.setCpfCnpj(configuracaoEmpresaVM.getCpfCnpj());
        return configEmpresa;
    }

    public static List<ConfiguracaoEmpresa> fromVM(List<ConfiguracaoEmpresaVM> configuracaoEmpresaVMs) {
        return configuracaoEmpresaVMs.stream().map(ConfiguracaoEmpresaMapper::fromVM).collect(Collectors.toList());
    }

    public static ConfiguracaoEmpresaVM toVM(ConfiguracaoEmpresa configuracaoEmpresa) {
        final ConfiguracaoEmpresaVM configEmpresaVM = new ConfiguracaoEmpresaVM();
        configEmpresaVM.setId(configuracaoEmpresa.getId());
        configEmpresaVM.setTipoPessoa(configuracaoEmpresa.getTipoPessoa());
        configEmpresaVM.setNomeRazao(configuracaoEmpresa.getNomeRazao());
        configEmpresaVM.setFantasia(configuracaoEmpresa.getFantasia());
        configEmpresaVM.setFantasiaCurta(configuracaoEmpresa.getFantasiaCurta());
        configEmpresaVM.setCpfCnpj(configuracaoEmpresa.getCpfCnpj());
        return configEmpresaVM;
    }

    public static List<ConfiguracaoEmpresaVM> toVM(List<ConfiguracaoEmpresa> configuracaoEmpresas) {
        return configuracaoEmpresas.stream().map(ConfiguracaoEmpresaMapper::toVM).collect(Collectors.toList());
    }

}
