package br.com.virtualab.newproject.model.usuario;

import br.com.virtualab.newproject.model.autoridade.Autoridade;
import br.com.virtualab.newproject.model.model.Model;
import br.com.virtualab.newproject.model.model.ModelImpl;
import br.com.virtualab.newproject.security.SecurityProperties;
import br.com.virtualab.newproject.util.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false, of = {"id"})
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"login"})
    ,@UniqueConstraint(columnNames = {"email"})
})
public class Usuario extends Model implements ModelImpl<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Campo login é obrigatório.")
    @Size(min = 3, max = 50, message = "Campo login deve ter entre {min} e {max} caracteres.")
    @Column(length = 50, nullable = false)
    private String login;

    @NotNull(message = "Campo password é obrigatório.")
    @Size(min = 60, max = 60, message = "Campo password deve ter entre {min} e {max} caracteres.")
    @Column(length = 60, nullable = false)
    private String password;

    @NotNull(message = "Campo nome é obrigatório.")
    @Size(min = 3, max = 20, message = "Campo nome deve ter entre {min} e {max} caracteres.")
    @Column(length = 20, nullable = false)
    private String nome;

    @NotNull(message = "Campo Sobre Nome é obrigatório.")
    @Size(min = 3, max = 50, message = "Campo Sobre Nome deve ter entre {min} e {max} caracteres.")
    @Column(name = "sobre_nome", length = 50, nullable = false)
    private String sobreNome;

    @NotNull(message = "Campo e-mail é obrigatório.")
    @Size(min = 8, max = 100, message = "Campo e-mail deve ter entre {min} e {max} caracteres.")
    @Column(length = 100, nullable = false)
    private String email;

    @NotNull(message = "Campo ativo é obrigatório.")
    @Column(nullable = false)
    private boolean ativo = false;

    @NotNull(message = "Campo ativado é obrigatório.")
    @Column(nullable = false)
    private boolean ativado = false;

    @JsonIgnore
    @Size(min = 20, max = 20, message = "Campo Chave Ativação deve ter entre {min} e {max} caracteres.")
    @Column(name = "chave_ativacao", length = 20)
    private String chaveAtivacao;

    @JsonIgnore
    @Size(min = 20, max = 20, message = "Campo Chave Reset deve ter entre {min} e {max} caracteres.")
    @Column(name = "chave_reset", length = 20)
    private String chaveReset;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao = DateTimeUtils.getLocalDateTimeNow();

    @Column(name = "data_ultimo_login", nullable = false)
    private LocalDateTime dataUltimoLogin = DateTimeUtils.getLocalDateTimeNow();

    @Column(name = "data_reset", nullable = false)
    private LocalDateTime dataReset = DateTimeUtils.getLocalDateTimeNow();

    @ManyToMany
    @JoinTable(name = "usuario_autoridade",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "autoridade_nome"))
    private List<Autoridade> autoridades = new ArrayList<>();

    @Column(name = "tentativa_login", nullable = false)
    private int tentativaLogin = 0;

    @Column(name = "data_ultima_tentativa", nullable = false)
    private LocalDateTime dataUltimaTentativa = DateTimeUtils.getLocalDateTimeNow();

    @Override
    public Long getId() {
        return id;
    }

    public boolean isBloqueadoTentativaLogin() {
        return this.getTentativaLogin() >= SecurityProperties.NR_TENTATIVA_LOGIN
                && this.getDataUltimaTentativa().plusMinutes(SecurityProperties.MINUTOS_TENTATIVA_LOGIN).compareTo(DateTimeUtils.getLocalDateTimeNow()) >= 0;
    }

    public String getDataTentativaLoginEm() {
        return this.getDataUltimaTentativa().plusMinutes(SecurityProperties.MINUTOS_TENTATIVA_LOGIN).format(DateTimeUtils.formatDateTime());
    }

}
