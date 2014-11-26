package vio.service.rest.doc;

import vio.service.rest.AbstractRS;
import javax.ejb.EJB;
import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Response;
import vio.model.doc.Document;
import vio.service.doc.DocumentFacade;

/**
 * @author moroz
 */
@Path("/doc")
public class DocumentRS extends AbstractRS {

    @EJB
    DocumentFacade facade;

    @GET
    @Path("{id:\\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Document getDocumentByID(@PathParam("id") int id) {
        return facade.get(id);
    }

    @GET
    @Path("{id:\\d+}/doctype")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDocumentType(@PathParam("id") int id) {
        return Response
                .ok()
                .entity(facade.getDocumentType(id))
                .build();
    }

    @GET
    @Path("{id:\\d+}/description")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getDocumentDescription(@PathParam("id") int id) {
    
        return Response
                .ok()//TODO: this dirty hack to avoid angularjs parse strings with '{' as json object
                .entity(facade.getDocumentDescription(id)/*.replace("{", "//")*/)
                .build();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDocumentList(@HeaderParam("X-Range") String headerRange) {
        return getItemsList(facade, headerRange);
    }
}
