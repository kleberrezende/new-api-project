package br.com.virtualab.newproject.repository.securitytoken;

import br.com.virtualab.newproject.model.securitytoken.QSecurityToken;
import br.com.virtualab.newproject.model.securitytoken.SecurityToken;
import br.com.virtualab.newproject.model.usuario.Usuario;
import br.com.virtualab.newproject.repository.Abstract.AbstractRepository;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class SecurityTokenRepository extends AbstractRepository<SecurityToken, String> {

    public Optional<SecurityToken> buscarPorToken(String token) {
        JPAQuery<?> query = createJPAQuery();
        QSecurityToken qSecurityToken = QSecurityToken.securityToken;
        SecurityToken securityToken = query.select(qSecurityToken)
                .from(qSecurityToken)
                .where(qSecurityToken.token.eq(token))
                .fetchOne();
        return Optional.ofNullable(securityToken);
    }

    public Optional<SecurityToken> buscarPorUsuario(Usuario usuario) {
        JPAQuery<?> query = createJPAQuery();
        QSecurityToken qSecurityToken = QSecurityToken.securityToken;
        SecurityToken securityToken = query.select(qSecurityToken)
                .from(qSecurityToken)
                .where(qSecurityToken.usuario.eq(usuario))
                .fetchOne();
        return Optional.ofNullable(securityToken);
    }

    public Optional<SecurityToken> buscarPorIdRequest(String idRequest) {
        JPAQuery<?> query = createJPAQuery();
        QSecurityToken qSecurityToken = QSecurityToken.securityToken;
        SecurityToken securityToken = query.select(qSecurityToken)
                .from(qSecurityToken)
                .where(qSecurityToken.idRequest.eq(idRequest))
                .fetchOne();
        return Optional.ofNullable(securityToken);
    }

    public List<SecurityToken> buscarPorUsuarioOuIdRequest(Usuario usuario, String idNavegador) {
        JPAQuery<?> query = createJPAQuery();
        QSecurityToken qSecurityToken = QSecurityToken.securityToken;
        return query.select(qSecurityToken)
                .from(qSecurityToken)
                .where(qSecurityToken.usuario.eq(usuario)
                        .or(qSecurityToken.idRequest.eq(idNavegador)))
                .fetch();
    }

}
