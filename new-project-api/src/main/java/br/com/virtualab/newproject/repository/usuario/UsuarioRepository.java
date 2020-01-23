package br.com.virtualab.newproject.repository.usuario;

import br.com.virtualab.newproject.model.usuario.QUsuario;
import br.com.virtualab.newproject.model.usuario.Usuario;
import br.com.virtualab.newproject.repository.Abstract.AbstractRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepository extends AbstractRepository<Usuario, Long> {

    public Optional<Usuario> buscarPorAutenticacao(String autenticacao) {
        JPAQuery<?> query = createJPAQuery();
        QUsuario qUsuario = QUsuario.usuario;
        Usuario usuario = query.select(qUsuario)
                .from(qUsuario)
                .where(qUsuario.login.eq(autenticacao)
                        .or(qUsuario.email.eq(autenticacao)))
                .fetchOne();
        return Optional.ofNullable(usuario);
    }

    public Optional<Usuario> buscarPorLogin(String login) {
        JPAQuery<?> query = createJPAQuery();
        QUsuario qUsuario = QUsuario.usuario;
        Usuario usuario = query.select(qUsuario)
                .from(qUsuario)
                .where(qUsuario.login.eq(login))
                .fetchOne();
        return Optional.ofNullable(usuario);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        JPAQuery<?> query = createJPAQuery();
        QUsuario qUsuario = QUsuario.usuario;
        Usuario usuario = query.select(qUsuario)
                .from(qUsuario)
                .where(qUsuario.email.eq(email))
                .fetchOne();
        return Optional.ofNullable(usuario);
    }

    public Optional<Usuario> buscarPorChaveReset(String chaveReset) {
        JPAQuery<?> query = createJPAQuery();
        QUsuario qUsuario = QUsuario.usuario;
        Usuario usuario = query.select(qUsuario)
                .from(qUsuario)
                .where(qUsuario.chaveReset.eq(chaveReset))
                .fetchOne();
        return Optional.ofNullable(usuario);
    }

    public Optional<Usuario> buscarPorChaveAtivacao(String chaveAticacao) {
        JPAQuery<?> query = createJPAQuery();
        QUsuario qUsuario = QUsuario.usuario;
        Usuario usuario = query.select(qUsuario)
                .from(qUsuario)
                .where(qUsuario.chaveAtivacao.eq(chaveAticacao))
                .fetchOne();
        return Optional.ofNullable(usuario);
    }

    public List<Usuario> buscarUsuarios(BooleanBuilder where) {
        JPAQuery<?> query = createJPAQuery();
        QUsuario qUsuario = QUsuario.usuario;
        List<Usuario> usuarios = query.select(qUsuario)
                .from(qUsuario)
                .where(where)
                .fetch();
        return usuarios;
    }

}
