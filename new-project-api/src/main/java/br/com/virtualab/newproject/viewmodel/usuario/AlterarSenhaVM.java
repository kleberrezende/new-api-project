package br.com.virtualab.newproject.viewmodel.usuario;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(of = {"login"})
@Getter
@Setter
public final class AlterarSenhaVM {

    private String login;
    private String senhaAtual;
    private String novaSenha;
    private String confirmarSenha;

    public AlterarSenhaVM(String login, String senhaAtual, String novaSenha, String confirmarSenha) {
        this.login = login;
        this.senhaAtual = senhaAtual;
        this.novaSenha = novaSenha;
        this.confirmarSenha = confirmarSenha;
    }

}
