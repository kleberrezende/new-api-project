package br.com.virtualab.newproject.security;

public final class SecurityProperties {

    public static final String JWT_SECRET = "KR@tokenKey!123";

    public static final int JWT_TOKEN_VALIDITY_IN_HOURS = 24;

    public static final int JWT_TOKEN_VALIDITY_IN_HOURS_FOR_REMEMBER_ME = 48;

    public static final int NR_TENTATIVA_LOGIN = 5;

    public static final int MINUTOS_TENTATIVA_LOGIN = 15;

}
