package vio.service.rest.doc;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import vio.model.doc.Format;
import vio.service.doc.FormatFacade;
import vio.service.rest.AbstractRS;

/**
 * @author moroz
 */
@Path("/format")
public class FormatRS extends AbstractRS {

    @EJB
    FormatFacade facade;

    @GET
    @Path("{id:\\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Format getColorByID(@PathParam("id") int id) {
        return facade.get(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFormatList(@HeaderParam("X-Range") String headerRange) {
        return getItemsList(facade, headerRange);
    }
}
