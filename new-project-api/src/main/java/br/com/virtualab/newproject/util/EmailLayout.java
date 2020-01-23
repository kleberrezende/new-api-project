package br.com.virtualab.newproject.util;

public class EmailLayout {

    public static String openHtml() {
        StringBuilder msg = new StringBuilder();
        msg.append("<!DOCTYPE html>");
        msg.append("<html>");
        msg.append("    <head>");
        msg.append("        <meta charset=\"utf-8\">");
        msg.append("        <meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0'>");
        msg.append("    </head>");
        msg.append("    <body style='background-color:#fff; margin: 0 auto; width:700px;'>");
        return msg.toString();
    }

    public static String closeHtml() {
        StringBuilder msg = new StringBuilder();
        msg.append("    </body>");
        msg.append("</html>");
        return msg.toString();
    }

    public static String header() {
        StringBuilder msg = new StringBuilder();
        msg.append("<div style='width:700px; height: 100px;'>");
        msg.append("    <img style='width: 100%' src='https://meusite.com.br/image/email/header_email.png'/>");
        msg.append("</div>");
        msg.append("<div style='background-color: #EEE; padding: 20px; min-height: 150px; width:660px;'>");
        return msg.toString();
    }

    public static String footer() {
        StringBuilder msg = new StringBuilder();
        msg.append("</div>");
        msg.append("<div style='width:700px;  height: 75px;'>");
        msg.append("    <img style='width: 100%' src='https://meusite.com.br/image/email/footer_email.png'/>");
        msg.append("</div>");
        return msg.toString();
    }

    public static String style() {
        StringBuilder msg = new StringBuilder();
        msg.append("<style>");
        msg.append("    * { ");
        msg.append("        padding: 0;");
        msg.append("        margin: 0;");
        msg.append("    }");
        msg.append("</style>");
        return msg.toString();
    }

    public static String htmlEmailConfig(String conteudo) {
        StringBuilder msg = new StringBuilder();
        msg.append(openHtml());
        msg.append(style());
        msg.append(header());
        msg.append(conteudo);
        msg.append(footer());
        msg.append(closeHtml());
        return msg.toString();
    }

}
