package br.com.virtualab.newproject.util;

public class SistemaInfo {

    public static final String ID_PROJETO = "newproject";
    public static final String NOME_PROJETO = "New Project";
    public static final String NOME_EMPRESA = "VirtuaLab";
    public static final String VERSAO = "1.0";
    public static final String PATH_VERSAO_API = "/v" + VERSAO;

    public String getIdProjeto() {
        return ID_PROJETO;
    }

    public String getNomeProjeto() {
        return NOME_PROJETO;
    }

    public String getVersao() {
        return VERSAO;
    }

    public String getNomeEmpresa() {
        return NOME_EMPRESA;
    }

}
