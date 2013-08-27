package vio.service.rest.doc;

import javax.ejb.EJB;
import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.List;
import vio.model.doc.Document;
import vio.service.doc.DocumentFacade;

/**
 * @author moroz
 */
@Path("/doc")
public class DocumentRS {

    @EJB
    DocumentFacade facade;

    @GET
    @Path("{id:\\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Document getDocumentByID(@PathParam("id") int id) {
        return facade.get(Integer.valueOf(id));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Document> getDocumentList() {
        return facade.list();
    }
}
