package br.com.virtualab.newproject.util;

import java.math.BigDecimal;

public final class NumberUtils {

    /**
     * Verifica se o valor é nulo ou menor igual a zero
     *
     * @param valor:Long
     * @return verdadeiro ou falso
     */
    public static boolean isLongEmpty(Long valor) {
        return (valor == null || valor == 0);
    }

    /**
     * Verifica se o valor é nulo ou menor igual a zero
     *
     * @param valor:Integer
     * @return verdadeiro ou falso
     */
    public static boolean isIntegerEmpty(Integer valor) {
        return (valor == null || valor == 0);
    }

    /**
     * Verifica se o valor é nulo ou menor igual a zero
     *
     * @param valor:BigDecimal
     * @return verdadeiro ou falso
     */
    public static boolean isBigDecimalEmpty(BigDecimal valor) {
        return (valor == null || valor.compareTo(BigDecimal.ZERO) == 0);
    }

}
