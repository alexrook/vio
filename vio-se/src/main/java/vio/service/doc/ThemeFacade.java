package vio.service.doc;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import vio.model.doc.Document;
import vio.model.doc.Theme;
import vio.service.AbstractFacade;

/**
 *
 * @author moroz
 */
@Stateless
@LocalBean
public class ThemeFacade extends AbstractFacade<Theme> {

    @PersistenceContext
    EntityManager em;
    @EJB
    DocumentFacade docFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ThemeFacade() {
        super(Theme.class);
    }

    public Theme create(String theme) {
        Theme t = new Theme(theme);
        return create(t);
    }

    public Theme addDocument(int themeId, int docId) throws EntityNotFoundException {
        Document doc = docFacade.get(docId, true);
        Theme t = get(themeId, true);
        t.setDocs(addTo(t.getDocs(), doc));
        doc.setThemes(addTo(doc.getThemes(), t));
        save(t);
        docFacade.save(doc);
        return t;

    }
}
