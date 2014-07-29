/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vio.service.rest.doc;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import vio.model.doc.Color;
import vio.service.doc.ColorFacade;
import vio.service.rest.AbstractRS;

/**
 * @author moroz
 */
@Path("/color")
public class ColorRS extends AbstractRS {

    @EJB
    ColorFacade facade;

    @GET
    @Path("{id:\\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Color getColorByID(@PathParam("id") int id) {
        return facade.get(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getColorList(@HeaderParam("X-Range") String headerRange) {
        return getItemsList(facade, headerRange);
    }
}
