package br.com.virtualab.newproject.type;

public enum TipoAplicativoType {

    MOBILE("Aplicativo Mobile"),
    WEB("Aplicativo Web");

    private final String descricao;

    TipoAplicativoType(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
