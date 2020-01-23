package br.com.virtualab.newproject.util;

import java.util.Random;

public final class StringUtils {

    /**
     * Verifica se a string é nula ou vazia
     *
     * @param valor:String
     * @return verdadeiro ou falso
     */
    public static boolean isStringEmpty(String valor) {
        return (valor == null || valor.trim().isEmpty());
    }

    /**
     * Verifica se o texto contém número
     *
     * @param value:String
     * @return verdadeiro ou falso
     */
    public static boolean contemNumero(String value) {
        if (isStringEmpty(value)) {
            return false;
        } else {
            return value.replaceAll("[^0-9]", "").length() > 0;
        }
    }

    /**
     * Verifica se o texto contém letras alfabeto
     *
     * @param value:String
     * @return verdadeiro ou falso
     */
    public static boolean contemLetra(String value) {
        if (isStringEmpty(value)) {
            return false;
        } else {
            return value.replaceAll("[^a-zA-Z]", "").length() > 0;
        }
    }

    /**
     * Retorna string sem caracteres especiais
     *
     * @param value:String
     * @return String
     */
    public static String removerCaracteresEspeciais(String value) {
        if (isStringEmpty(value)) {
            return null;
        } else {
            return value.replaceAll("[^a-zA-Z0-9]", "");
        }
    }

    /**
     * Verifica se o texto contém apenas letras e números
     *
     * @param value:String
     * @return boolean
     */
    public static boolean apenasLetrasENumeros(String value) {
        if (isStringEmpty(value)) {
            return false;
        } else {
            return value.matches("^[a-zA-Z0-9]+$");
        }
    }

    /**
     * Gera um texto com caracteres aleatórios
     *
     * @param max:int
     * @return String
     */
    public static String gerarTextoAleatorio(int max) {
        String[] carct = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "@"};
        StringBuilder password = new StringBuilder();
        Random rdm = new Random();
        for (int x = 0; x < max; x++) {
            password.append(carct[rdm.nextInt(carct.length)]);
        }
        return password.toString();
    }

    /**
     * Retorna string preenchida pelo filler a esquerda
     *
     * @param valueToPad:String
     * @param filler:String
     * @param size:int
     * @return String
     */
    public static String lpad(String valueToPad, String filler, int size) {
        while (valueToPad.length() < size) {
            valueToPad = filler + valueToPad;
        }
        return valueToPad;
    }

    /**
     * Retorna string preenchida pelo filler a direita
     *
     * @param valueToPad:String
     * @param filler:String
     * @param size:int
     * @return String
     */
    public static String rpad(String valueToPad, String filler, int size) {
        while (valueToPad.length() < size) {
            valueToPad = valueToPad + filler;
        }
        return valueToPad;
    }

    /**
     * Retorna string para quebra de linha no código HTML
     *
     * @return String
     */
    public static String breakLineHTML() {
        return "<br/>";
    }

    /**
     * Aplica trim em uma String
     *
     * @param value:String
     */
    public static void trim(String value) {
        if (value != null) {
            value = value.trim();
        }
    }

}
