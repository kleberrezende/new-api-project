package br.com.virtualab.newproject.viewmodel.usuario;

import br.com.virtualab.newproject.type.TipoAplicativoType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(of = {"autenticacao"})
@Getter
@Setter
public final class EnviarAtivarUsuarioVM {

    private String autenticacao;
    private String uri;
    private TipoAplicativoType tipoAplicativo;

}
