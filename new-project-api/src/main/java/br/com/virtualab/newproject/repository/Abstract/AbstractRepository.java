package br.com.virtualab.newproject.repository.Abstract;

import com.querydsl.jpa.impl.JPAQuery;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import br.com.virtualab.newproject.model.model.ModelImpl;

public abstract class AbstractRepository<T extends ModelImpl<ID>, ID extends Serializable> {

    private Class<T> type;

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public AbstractRepository() {
        Class<?> clazz = getClass();
        do {
            if (clazz.getSuperclass().equals(AbstractRepository.class)) {
                break;
            }
        } while ((clazz = clazz.getSuperclass()) != null);

        this.type = (Class<T>) ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    protected JPAQuery createJPAQuery() {
        this.entityManager.clear();
        return new JPAQuery(this.entityManager);
    }

    @Transactional
    public void save(T model) {
        if (model.getId() == null) {
            this.entityManager.persist(model);
        } else {
            this.entityManager.merge(model);
        }
        this.entityManager.flush();
    }

    @Transactional
    public void remove(T model) {
        this.entityManager.remove(this.entityManager.getReference(this.type, model.getId()));
        this.entityManager.flush();
    }

    public T findById(ID id) {
        return this.entityManager.find(this.type, id);
    }

}
