package br.com.virtualab.newproject.viewmodel.usuario;

import br.com.virtualab.newproject.type.TipoAplicativoType;
import br.com.virtualab.newproject.util.DateTimeUtils;
import br.com.virtualab.newproject.viewmodel.autoridade.AutoridadeVM;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(of = {"id"})
@Getter
@Setter
public final class UsuarioVM {

    private Long id;
    private String login;
    private String nome;
    private String sobreNome;
    private String email;
    private boolean ativo = false;
    private boolean ativado = false;
    private LocalDateTime dataCriacao = DateTimeUtils.getLocalDateTimeNow();
    private LocalDateTime dataUltimoLogin = DateTimeUtils.getLocalDateTimeNow();
    private LocalDateTime dataReset = DateTimeUtils.getLocalDateTimeNow();
    private List<AutoridadeVM> autoridades = new ArrayList<>();
    private String uri;
    private TipoAplicativoType tipoAplicativo;
    private boolean bloqueadoTentativaLogin;

}
