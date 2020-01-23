package br.com.virtualab.newproject.util;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

public class Email {

    private final String SMTP_HOST = "smtp.umbler.com";
    private final int SMTP_PORT = 587;
    private final String MAIL_USER = "noreplay@meusite.com.br";
    private final String MAIL_PASSWORD = "????";
    private final boolean SSL_REQUIRED = false;
    private final int TIME_OUT = 30000;
    private final boolean DEBUG = false;
    private final String CHARSET = "UTF-8";

    private Map<String, String> sendToList = new HashMap<>();
    private String assunto = "";
    private StringBuilder msg = new StringBuilder();

    public void addTo(String email, String nome) {
        sendToList.put(email, nome);
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public void addMsg(String msg) {
        this.msg.append(msg);
    }

    private void configEmail(org.apache.commons.mail.Email email) throws EmailException {
        email.setHostName(SMTP_HOST);
        email.setSmtpPort(SMTP_PORT);
        email.setSSLOnConnect(SSL_REQUIRED);
        email.setSSLCheckServerIdentity(SSL_REQUIRED);
        email.setSocketConnectionTimeout(TIME_OUT);
        email.setSocketTimeout(TIME_OUT);
        email.setAuthentication(MAIL_USER, MAIL_PASSWORD);
        email.setDebug(DEBUG);
        email.setCharset(CHARSET);
        email.setFrom(MAIL_USER, SistemaInfo.NOME_EMPRESA);
        for (Map.Entry<String, String> sendTo : sendToList.entrySet()) {
            email.addTo(sendTo.getKey(), sendTo.getValue());
        }
        email.setSubject(assunto);
    }

    public void sendSimpleEmail() {
        try {
            SimpleEmail email = new SimpleEmail();
            configEmail(email);
            email.setMsg(msg.toString());
            email.send();
        } catch (Exception e) {
            Log.error(getClass(), e.getMessage());
        }
    }

    public void sendHtmlEmail() {
        try {
            HtmlEmail email = new HtmlEmail();
            configEmail(email);
            email.setTextMsg("Seu servidor de e-mail não suporta mensagem HTML.");
            email.setHtmlMsg(msg.toString());
            email.send();
        } catch (Exception e) {
            Log.error(getClass(), e.getMessage());
        }
    }

}
