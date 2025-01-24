package mn.unitel.endpoint;

import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


@Path("/search_form")
public class SearchEndpoint {

    @Inject
    Template searchForm;

    @GET
    public Response showSearchForm(@CookieParam("nameCookie") String encodedFullName) throws UnsupportedEncodingException {

        String fullName = URLDecoder.decode(encodedFullName, "UTF-8");
        String html = searchForm
                .data("title", "Search Form")
                .data("header", "Search CDR")
                .data("action", "/search_form/search")
                .data("fullName", fullName)
                .render();

        return Response.status(Response.Status.OK)
                .entity(html)
                .header("Content-Type", "text/html")
                .build();
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response redirectToMethod(@QueryParam("SearchKey") String searchKey, // "imei
                                     @QueryParam("SearchValue") String searchValue, // 356546966494290
                                     @QueryParam("Page") int page, // 2
                                     @QueryParam("PageSize") int pageSize // 3
    ) {

            String redirectUrl = String.format(
                    "http://localhost:8080/cdr/xml?searchKey=%s&searchValue=%s&page=%d&pageSize=%d",
                    searchKey, searchValue, page, pageSize
            );
            return Response.status(Response.Status.FOUND)
                    .header("Location", redirectUrl)
                    .build();
    }
}


