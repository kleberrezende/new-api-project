package br.com.virtualab.newproject.type;

public enum TipoPessoaType {

    FISICA("Física"),
    JURIDICA("Jurídica");

    private final String descricao;

    TipoPessoaType(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
