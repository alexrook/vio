package vio.service.doc;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import vio.model.doc.DocumentType;
import vio.service.AbstractFacade;

/**
 * @author moroz
 */
@Stateless
@LocalBean
public class DocumentTypeFacade extends AbstractFacade<DocumentType> {

    @PersistenceContext
    EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DocumentTypeFacade() {
        super(DocumentType.class);
    }
}
