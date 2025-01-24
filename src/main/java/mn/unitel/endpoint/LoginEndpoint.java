package mn.unitel.endpoint;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/login")
public class LoginEndpoint {

    private static final String CLIENT_ID = "9c6dbbd8-561c-4cdf-a784-890f54c5f3a8";
    private static final String REDIRECT_URI = "http://localhost:8080/callback";

    @GET
    public Response login() {

        String loginUrl = "https://sso.toki.mn/oauth2/authorize?" +
                "client_id=" + CLIENT_ID +
                "&response_type=code" +
                "&scope=openid%20email%20profile" +
                "&redirect_uri=" + REDIRECT_URI;
        return Response.status(Response.Status.FOUND).header("Location", loginUrl).build();
    }
}