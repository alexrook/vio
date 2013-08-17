package vio.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.Query;

public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public T create(T entity) {
        getEntityManager().persist(entity);
        return entity;
    }

    public T edit(T entity) {
        getEntityManager().merge(entity);
        return entity;
    }

    public T save(T entity) {
      return edit(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T get(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public T get(Object id, boolean throwErrorIfNull) throws EntityNotFoundException {
        T result = get(id);

        if ((result != null) || (!throwErrorIfNull)) {
            return result;
        } else {

            throw new EntityNotFoundException("entity with class="
                    + entityClass.getName()
                    + ", and id=" + id + " not found");
        }

    }

    public <E> Collection addTo(Collection<E> collection, E member) {
        if (collection != null) {
            collection.add(member);
        } else {
            collection = new ArrayList<E>();
            collection.add(member);
        }
        return collection;
    }

    public List<T> list() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> listByRange(int[] range) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}
