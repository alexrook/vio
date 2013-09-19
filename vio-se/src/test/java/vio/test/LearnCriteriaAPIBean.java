/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vio.test;

import java.util.Collection;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import vio.model.doc.Color;
import vio.model.doc.DocumentType;
import vio.model.doc.Theme;

/**
 *
 * @author moroz
 */
@Stateless
@LocalBean
public class LearnCriteriaAPIBean {

    @PersistenceContext
    EntityManager em;

    public Collection<Color> getColorsByName(String name) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Color> cq = cb.<Color>createQuery(Color.class);

        Root<Color> root = cq.from(Color.class);

        Predicate like = cb.like(root.<String>get("val"), name);
        cq.select(root).where(like);
        Query q = em.createQuery(cq);
        return q.getResultList();

    }

    /*
     * A query may have more than one root. The addition of a query root has the semantic effect of creating a
     *cartesian product between the entity type referenced by the added root and those of the other roots.
     *
     */
    public Collection<Tuple> getCartesianProduct() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();

        Root<Color> colorRoot = cq.from(Color.class);
        Root<Theme> themeRoot = cq.from(Theme.class);

        cq.multiselect(colorRoot, themeRoot);

        Query q = em.createQuery(cq);
        return q.getResultList();

    }

    public Collection<DocumentType> getFetch() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DocumentType> cq = cb.createQuery(DocumentType.class);

        Root<DocumentType> doctypeRoot = cq.from(DocumentType.class);

        doctypeRoot.fetch("childDocTypes", JoinType.INNER);

        cq.select(doctypeRoot);


        Query q = em.createQuery(cq);
        return q.getResultList();

    }
}
