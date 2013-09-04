/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vio.service.rest.doc;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getColorList(@HeaderParam("Range") String headerRange) {
        int[] range = getRangeFromHeader(headerRange);
        return Response.ok()
                .entity(facade.listByRange(range))
                .header("Content-Range", "0-1").build();

    }
}
