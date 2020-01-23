package br.com.virtualab.newproject.model.autoridade;

import br.com.virtualab.newproject.model.model.Model;
import br.com.virtualab.newproject.model.model.ModelImpl;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false, of = {"nome"})
@Entity
public class Autoridade extends Model implements ModelImpl<String> {

    @Id
    @NotNull(message = "Campo nome é obrigatório.")
    @Size(min = 3, max = 50, message = "Campo nome deve ter entre {min} e {max} caracteres.")
    @Column(length = 50, nullable = false)
    private String nome;

    @NotNull(message = "Campo descrição é obrigatório.")
    @Size(min = 3, max = 50, message = "Campo nome deve ter entre {min} e {max} caracteres.")
    @Column(length = 50, nullable = false)
    private String descricao;

    @Override
    public String getId() {
        return nome;
    }

}
