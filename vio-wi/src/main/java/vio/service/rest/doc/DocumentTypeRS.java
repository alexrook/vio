package vio.service.rest.doc;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import vio.model.doc.DocumentType;
import vio.service.doc.DocumentTypeFacade;
import vio.service.rest.AbstractRS;

/**
 * @author moroz
 */
@Path("/doctype")
public class DocumentTypeRS extends AbstractRS {

    @EJB
    DocumentTypeFacade facade;

    @GET
    @Path("{id:\\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public DocumentType getDocumentTypeByID(@PathParam("id") int id) {
        return facade.get(Integer.valueOf(id));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDocumentTypeList() {
        
        return Response.ok()
                .entity(facade.list()).build();

    }

}
