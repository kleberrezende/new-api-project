package br.com.virtualab.newproject.model.model;

import br.com.virtualab.newproject.exception.BusinessException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class Model {

    @PreUpdate
    @PrePersist
    public void repairFiels() {
        repairFields(this.getClass(), this);
    }

    private static void repairFields(Class clazz, Object obj) {
        try {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.getType() == String.class
                        && !Modifier.isFinal(field.getModifiers())
                        && !field.isAnnotationPresent(Transient.class)) {
                    field.setAccessible(true);
                    if (field.get(obj) != null) {
                        field.set(obj, ((String) field.get(obj)).trim());
                    }
                    field.setAccessible(false);
                }
            }
        } catch (Exception ex) {
            throw new BusinessException("Erro em PrePersist/PreUpdate da entidade ao reparar campo.");
        }
    }

}
