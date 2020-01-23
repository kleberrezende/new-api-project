package br.com.virtualab.newproject.model.securitytoken;

import br.com.virtualab.newproject.model.model.Model;
import br.com.virtualab.newproject.model.model.ModelImpl;
import br.com.virtualab.newproject.model.usuario.Usuario;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = {"token"})
@Entity
@Table(name = "security_token",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"id_request"})
            ,@UniqueConstraint(columnNames = {"usuario_id"})
        })

public class SecurityToken extends Model implements ModelImpl<String> {

    @Id
    @NotNull(message = "Campo token é obrigatório.")
    @Column(length = 750, nullable = false)
    private String token;

    @NotNull(message = "Campo ID Request é obrigatório.")
    @Column(name = "id_request", length = 600, nullable = false)
    private String idRequest;

    @NotNull(message = "Campo Usuário é obrigatório.")
    @OneToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Override
    public String getId() {
        return token;
    }

}
