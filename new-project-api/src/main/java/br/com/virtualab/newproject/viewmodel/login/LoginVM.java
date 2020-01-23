package br.com.virtualab.newproject.viewmodel.login;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class LoginVM {

    @NotNull(message = "Campo Login é obrigatório.")
    @Size(min = 3, max = 50, message = "Campo Login deve ter entre {min} e {max} caracteres.")
    private String autenticacao;

    @NotNull(message = "Campo Senha é obrigatório.")
    @Size(min = 8, max = 20, message = "Campo Senha deve ter entre {min} e {max} caracteres.")
    private String password;

    @NotNull(message = "Campo Lembrar-me é obrigatório.")
    private boolean rememberMe;

}
