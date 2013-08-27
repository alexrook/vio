package vio.service.doc;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import vio.model.doc.Format;
import vio.service.AbstractFacade;

/**
 * @author moroz
 */
@Stateless
@LocalBean
public class FormatFacade extends AbstractFacade<Format> {

    @PersistenceContext
    EntityManager em;

    public FormatFacade() {
        super(Format.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
