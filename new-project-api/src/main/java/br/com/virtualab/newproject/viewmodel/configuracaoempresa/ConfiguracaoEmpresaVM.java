package br.com.virtualab.newproject.viewmodel.configuracaoempresa;

import br.com.virtualab.newproject.type.TipoPessoaType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString
public final class ConfiguracaoEmpresaVM {

    private Long id = 1L;
    private TipoPessoaType tipoPessoa = TipoPessoaType.JURIDICA;
    private String nomeRazao = "Empresa";
    private String fantasia = "Fantasia";
    private String fantasiaCurta = "Fantasia curta";
    private String cpfCnpj;

}
