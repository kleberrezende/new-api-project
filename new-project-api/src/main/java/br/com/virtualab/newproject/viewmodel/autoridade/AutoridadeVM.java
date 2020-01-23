package br.com.virtualab.newproject.viewmodel.autoridade;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(of = {"nome"})
@Getter
@Setter
public final class AutoridadeVM {

    private String nome;
    private String descricao;

}
