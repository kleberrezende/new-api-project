package br.com.virtualab.newproject.util;

public final class EncryptBasic {

    private static final String NORMAL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789*/-+.,:;?!|=&@#$";

    private static final String ENGRYPT = "uvN&OPT;?stUVWXYZqr3:QRS!|=wxyzABFLMGHIJK@#$012CDE49*5678lmnop/-+.,abcdefghijk";

    private static final String MSG = "Constantes de criptografia não são idênticas.";

    private static boolean isEncryptValid() {
        if (NORMAL.length() == ENGRYPT.length()) {
            for (int pos = 0; pos <= NORMAL.length() - 1; pos++) {
                if (ENGRYPT.indexOf(NORMAL.charAt(pos)) == -1) {
                    return false;
                }
            }
            for (int pos = 0; pos <= ENGRYPT.length() - 1; pos++) {
                if (NORMAL.indexOf(ENGRYPT.charAt(pos)) == -1) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public static String encrypt(String value) {
        if (value != null) {
            if (isEncryptValid()) {
                String result = "";
                for (int pos = 0; pos <= value.length() - 1; pos++) {
                    if (NORMAL.indexOf(value.charAt(pos)) >= 0) {
                        result = result + ENGRYPT.charAt(NORMAL.indexOf(value.charAt(pos)));
                    } else {
                        result = result + value.charAt(pos);
                    }
                }
                return result;
            } else {
                return MSG;
            }
        } else {
            return value;
        }
    }

    public static String decrypt(String value) {
        if (value != null) {
            if (isEncryptValid()) {
                String result = "";
                for (int pos = 0; pos <= value.length() - 1; pos++) {
                    if (ENGRYPT.indexOf(value.charAt(pos)) >= 0) {
                        result = result + NORMAL.charAt(ENGRYPT.indexOf(value.charAt(pos)));
                    } else {
                        result = result + value.charAt(pos);
                    }
                }
                return result;
            } else {
                return MSG;
            }
        } else {
            return value;
        }
    }

}
