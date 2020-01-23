package br.com.virtualab.newproject.model.configuracaoempresa;

import br.com.virtualab.newproject.model.model.Model;
import br.com.virtualab.newproject.model.model.ModelImpl;
import br.com.virtualab.newproject.type.TipoPessoaType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false, of = {"id"})
@Entity
@Table(name = "configuracao_empresa")
public class ConfiguracaoEmpresa extends Model implements ModelImpl<Long> {

    @Id
    @Column(name = "id")
    private Long id = 1L;

    @NotNull(message = "Campo Tipo pessoa é obrigatório.")
    @Column(name = "tipo_pessoa", length = 8, nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoPessoaType tipoPessoa = TipoPessoaType.JURIDICA;

    @NotNull(message = "Campo Nome/Razão é obrigatório.")
    @Size(min = 3, max = 60, message = "Campo Nome/Razão deve ter entre {min} e {max} caracteres.")
    @Column(name = "nome_razao", length = 60, nullable = false)
    private String nomeRazao = "Empresa";

    @NotNull(message = "Campo Fantasia é obrigatório.")
    @Size(min = 3, max = 50, message = "Campo Fantasia deve ter entre {min} e {max} caracteres.")
    @Column(name = "fantasia", length = 50, nullable = false)
    private String fantasia = "Fantasia";

    @NotNull(message = "Campo Fantasia curta é obrigatório.")
    @Size(min = 3, max = 20, message = "Campo Fantasia curta deve ter entre {min} e {max} caracteres.")
    @Column(name = "fantasia_curta", length = 20, nullable = false)
    private String fantasiaCurta = "Fantasia curta";

    @NotNull(message = "Campo CPF/CNPJ é obrigatório.")
    @Size(min = 11, max = 15, message = "Campo CPF/CNPJ deve ter entre {min} e {max} caracteres.")
    @Column(name = "cpf_cnpj", length = 15, nullable = false)
    private String cpfCnpj;

    @Override
    public Long getId() {
        return id;
    }

}
