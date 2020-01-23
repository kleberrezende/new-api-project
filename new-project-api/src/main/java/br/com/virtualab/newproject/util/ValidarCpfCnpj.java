package br.com.virtualab.newproject.util;

public class ValidarCpfCnpj {

    public static boolean isCpfCnpj(String cpfCnpj) {
        if (StringUtils.isStringEmpty(cpfCnpj)) {
            return false;
        } else if (cpfCnpj.length() <= 11) {
            return ValidarCpf.isCPF(cpfCnpj);
        } else {
            return ValidarCnpj.isCNPJ(cpfCnpj);
        }
    }

}
