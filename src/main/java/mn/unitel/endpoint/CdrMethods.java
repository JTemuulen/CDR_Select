package mn.unitel.endpoint;

import io.quarkus.security.Authenticated;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.vertx.core.json.JsonArray;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mn.unitel.service.DBService;
import mn.unitel.service.HTMLService;
import org.jboss.resteasy.reactive.RestQuery;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Path("/cdr")
public class CdrMethods {

    @Inject
    DBService dbService;

    @Inject
    JWTParser jwtParser;

    @Inject
    HTMLService htmlService;

    @GET

    @Produces(MediaType.APPLICATION_JSON)
    public Response getCdr(@RestQuery String serviceType) {
        return Response.ok(dbService.selectCdr(serviceType)).build();
    }

    @GET
    @Path("/xml")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserXml(
            @CookieParam("authToken") String token,
            @QueryParam("searchKey") String searchKey,
            @QueryParam("searchValue") String searchValue,
            @QueryParam("page") int page,
            @QueryParam("pageSize") int pageSize,
            @CookieParam("nameCookie") String encodedFullName
    ) {
        System.out.println("cookie in /cdr/xml: " + token);
        try {
            jwtParser.parse(token);
            System.out.println("token is correct");

            JsonArray cdrData = dbService.selectCdrXml(searchKey, searchValue, page, pageSize);
            int rowCount = dbService.countRowsCdrXml(searchKey, searchValue);

            return htmlService.JsonToHTML(cdrData, searchKey, searchValue, page, pageSize, rowCount, encodedFullName);


        }catch (ParseException e) {

            return Response.status(Response.Status.UNAUTHORIZED).build();

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }
}