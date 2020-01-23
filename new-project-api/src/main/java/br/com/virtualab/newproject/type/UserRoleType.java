package br.com.virtualab.newproject.type;

import org.springframework.security.core.GrantedAuthority;

public enum UserRoleType implements GrantedAuthority {

    ROLE_ADMIN("Administrador"),
    ROLE_ANONYMOUS("Anônimo"),
    ROLE_USER("Usuário");

    public static final String PATH_ADMIN = "/admin";
    public static final String PATH_ANONYMOUS = "/anonimo";
    public static final String PATH_USER = "/user";

    @Override
    public String getAuthority() {
        return name();
    }

    private final String descricao;

    UserRoleType(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
