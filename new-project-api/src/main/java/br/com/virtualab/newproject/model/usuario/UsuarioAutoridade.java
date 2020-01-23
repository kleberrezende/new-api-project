package br.com.virtualab.newproject.model.usuario;

import br.com.virtualab.newproject.model.autoridade.Autoridade;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false, of = {"usuario", "autoridade"})
@Entity
@Table(name = "usuario_autoridade")
public class UsuarioAutoridade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull(message = "Campo Usuário é obrigatório.")
    @JoinColumn(name = "usuario_id", nullable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    @Id
    @NotNull(message = "Campo Autoridade é obrigatório.")
    @JoinColumn(name = "autoridade_nome", nullable = false)
    @ManyToOne(optional = false)
    private Autoridade autoridade;

}
