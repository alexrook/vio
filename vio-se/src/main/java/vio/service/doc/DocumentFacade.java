package vio.service.doc;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import vio.service.AbstractFacade;
import vio.model.doc.Document;

/**
 *
 * @author moroz
 */
@Stateless
@LocalBean
public class DocumentFacade extends AbstractFacade<Document> {

    @PersistenceContext
    EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DocumentFacade() {
        super(Document.class);
    }
}
