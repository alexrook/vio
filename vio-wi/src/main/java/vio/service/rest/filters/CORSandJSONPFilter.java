package vio.service.rest.filters;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author moroz
 */
public class CORSandJSONPFilter extends GenericFilter {

    //this parameter must be in web.xml
    public static final String JSONP_PARAMETER_NAME = "JSONPParameter";
    //https://developer.mozilla.org/en-US/docs/HTTP/Access_control_CORS
    public static final String REQUEST_ORIGIN_HEADER = "Origin";
    public static final String RESPONSE_ALLOW_ORIGIN = "Access-Control-Allow-Origin";

    public CORSandJSONPFilter() {
    }

    @Override
    public final void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {

        log("JSONPFilter:DoBeforeProcessing");

        String jsonPadding = getJsonPadding(request);

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if ((jsonPadding != null) && (jsonPadding.length() > 0)) {
            httpResponse.getOutputStream().println(jsonPadding + "(");
        }

    }

    @Override
    public final void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {

        log("JSONPFilter:DoAfterProcessing");
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String jsonPadding = getJsonPadding(request);

        if ((jsonPadding != null) && (jsonPadding.length() > 0)) {

            response.getOutputStream().println(")");
        }

        if (getOrigin(httpRequest) != null) {
            httpResponse.setHeader(RESPONSE_ALLOW_ORIGIN, "*");
        }
    }

    private String getJsonPadding(ServletRequest request) {
        try {
            return request.getParameter(
                    getFilterConfig().getInitParameter(JSONP_PARAMETER_NAME)
            );
        } catch (Exception e) {
            return null;
        }
    }

    private String getOrigin(HttpServletRequest request) {
        String result = request.getHeader(REQUEST_ORIGIN_HEADER);
        log("JSONPFilter:Request Origin:" + result);
        return result;
    }
}
