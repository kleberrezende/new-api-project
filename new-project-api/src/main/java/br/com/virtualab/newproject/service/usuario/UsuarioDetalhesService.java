package br.com.virtualab.newproject.service.usuario;

import br.com.virtualab.newproject.model.usuario.Usuario;
import br.com.virtualab.newproject.type.TipoAplicativoType;
import br.com.virtualab.newproject.util.Email;
import br.com.virtualab.newproject.util.EmailLayout;
import br.com.virtualab.newproject.util.SistemaInfo;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetalhesService {

    public void sendEmailSenha(Usuario usuario, String senha) {
        StringBuilder conteudo = new StringBuilder();
        conteudo.append("<span>  Sistema: " + SistemaInfo.NOME_PROJETO + "</span>").append("<br>");
        conteudo.append("<span> Sua nova senha de acesso ao sistema é: " + senha + "</span>").append("<br>");

        Email email = new Email();
        email.setAssunto("Senha de acesso ao sistema.");
        email.addTo(usuario.getEmail(), usuario.getNome());
        email.addMsg(EmailLayout.htmlEmailConfig(conteudo.toString()));
        email.sendHtmlEmail();
    }

    public void sendEmailAtivarUsuario(Usuario usuario, String uri, TipoAplicativoType tipoAplicativo) {
        StringBuilder conteudo = new StringBuilder();
        conteudo.append("<span>  Sistema: " + SistemaInfo.NOME_PROJETO + "</span>").append("<br>");
        if (tipoAplicativo.equals(TipoAplicativoType.WEB)) {
            conteudo.append("<span> Clique no link a baixo para ativar seu usuário: </span>").append("<br>");
            conteudo.append("<a href='" + uri + "/" + usuario.getChaveAtivacao() + "'>Ativar Usuário</a>").append("<br>");
        } else if (tipoAplicativo.equals(TipoAplicativoType.MOBILE)) {
            conteudo.append("<span> Código de ativação: ").append(usuario.getChaveAtivacao()).append("</span>").append("<br>");
            conteudo.append("<span> Entre com o código na opção de ativar usuário no aplicativo. </span>").append("<br>");
        }

        Email email = new Email();
        email.setAssunto("Ativar usuário.");
        email.addTo(usuario.getEmail(), usuario.getNome());
        email.addMsg(EmailLayout.htmlEmailConfig(conteudo.toString()));
        email.sendHtmlEmail();
    }

    public void sendEmailResetarUsuario(Usuario usuario, String uri, TipoAplicativoType tipoAplicativo) {
        StringBuilder conteudo = new StringBuilder();
        conteudo.append("<span>  Sistema: " + SistemaInfo.NOME_PROJETO + "</span>").append("<br>");
        if (tipoAplicativo.equals(TipoAplicativoType.WEB)) {
            conteudo.append("<span> Clique no link a baixo para resetar seu usuário: </span>").append("<br>");
            conteudo.append("<a href='" + uri + "/" + usuario.getChaveReset() + "'>Resetar Usuário</a>").append("<br>");
        } else if (tipoAplicativo.equals(TipoAplicativoType.MOBILE)) {
            conteudo.append("<span> Código de reset: ").append(usuario.getChaveReset()).append("</span>").append("<br>");
            conteudo.append("<span> Entre com o código na opção de resetar usuário no aplicativo. </span>").append("<br>");
        }

        Email email = new Email();
        email.setAssunto("Resetar usuário.");
        email.addTo(usuario.getEmail(), usuario.getNome());
        email.addMsg(EmailLayout.htmlEmailConfig(conteudo.toString()));
        email.sendHtmlEmail();
    }

}
