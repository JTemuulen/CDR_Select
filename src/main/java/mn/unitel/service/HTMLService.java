package mn.unitel.service;

import io.quarkus.qute.Template;
import io.vertx.core.json.JsonArray;
import jakarta.enterprise.context.ApplicationScoped;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.core.Response;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@ApplicationScoped
public class HTMLService {

    @Inject
    Template jsonTable;

    public Response JsonToHTML(JsonArray cdrData,
                               String searchKey, String searchValue,
                               int page,
                               int pageSize,
                               int rowCount,
                               String encodedFullName) throws UnsupportedEncodingException {

        int lastPage = (rowCount + pageSize - 1) / pageSize;
        String previousPage = (page > 1) ? String.valueOf(page - 1) : "1";
        String nextPage = (page < lastPage) ? String.valueOf(page + 1) : String.valueOf(lastPage);

        String fullName = URLDecoder.decode(encodedFullName, "UTF-8");

        String htmlResponse = jsonTable
                .data("title", "CDR Data")
                .data("fullName", fullName)
                .data("header", "CDR Data")
                .data("tableUrl", "/cdr/xml")
                .data("searchUrl", "/search_form")
                .data("jsonArray", cdrData)  // Pass cdrData here
                .data("searchKey", searchKey)
                .data("searchValue", searchValue)
                .data("page", page)
                .data("pageSize", pageSize)
                .data("previousPage", previousPage)
                .data("nextPage", nextPage)
                .data("lastPage", lastPage)
                .render();

        return Response.status(Response.Status.OK)
                .entity(htmlResponse)
                .header("Content-Type", "text/html")
                .build();
    }
}

