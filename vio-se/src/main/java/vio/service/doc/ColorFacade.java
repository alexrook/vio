package vio.service.doc;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import vio.model.doc.Color;
import vio.service.AbstractFacade;

/**
 *
 * @author moroz
 */
@Stateless
@LocalBean
public class ColorFacade extends AbstractFacade<Color>{

    @PersistenceContext
    EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
    public ColorFacade(){
        super(Color.class);
    }

   

}
