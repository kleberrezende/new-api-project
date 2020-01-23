package br.com.virtualab.newproject.viewmodel.usuario;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(of = {"chave"})
@Getter
@Setter
public class ResetarUsuarioVM {

    private String chave;
    private String novaSenha;
    private String confirmarSenha;

}
