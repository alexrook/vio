package vio.service.doc;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import vio.model.doc.Document;
import vio.model.doc.DocumentType;
import vio.service.AbstractFacade;

/**
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

    public DocumentType getDocumentType(int docId) {
        return em.createNamedQuery("Document.docType", DocumentType.class)
                .setParameter("docId", docId)
                .getSingleResult();
    }
    
    public String getDocumentDescription(int docId) {
        return em.createNamedQuery("Document.description", String.class)
                .setParameter("docId", docId)
                .getSingleResult();
    }
}
