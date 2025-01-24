package mn.unitel.endpoint;

import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import mn.unitel.service.DBService;
import mn.unitel.service.SSOService;
import mn.unitel.service.TokenService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Path("/callback")
public class CallbackEndpoint {
    @RestClient
    @Inject
    SSOService service;
    @Inject
    TokenService tokenService;
    @Inject
    DBService dbService;


    @GET
    public Response callback(@QueryParam("code") String code) throws UnsupportedEncodingException {

        JsonObject accessObject = service.getAccessToken("9c6dbbd8-561c-4cdf-a784-890f54c5f3a8",
                "e4yeYqBFjDLWlM-oT0AsCud9j-fEkCBWvfFsCjGaYho",
                "http://localhost:8080/callback",
                code,
                "authorization_code");

        String userId = accessObject.getString("userId");
        JsonObject userInfo = service.getUserInfo("Bearer " + accessObject.getString("access_token"), accessObject.getString("userId"));
        JsonObject userData = userInfo.getJsonObject("data");
        dbService.insertUser(userData.getString("firstName"), userData.getString("lastName"), userData.getString("nationalId"), userData.getString("dob"), userData.getString("phoneNo"));

        String fullName = userData.getString("firstName") + " " + userData.getString("lastName");
        String encodedName = URLEncoder.encode(fullName, "UTF-8");
        NewCookie nameCookie = new NewCookie("nameCookie", encodedName, "/", null, null, 3600, true, true);

        String token = tokenService.generateToken(userId);
        System.out.println("Generated Token: " + token);
        NewCookie tokenCookie = new NewCookie("authToken", token, "/", null, null, 3600, true, true);


        String REDIRECT_URI = "http://localhost:8080/search_form";
        String callbackUrl = REDIRECT_URI;
        return Response.status(Response.Status.FOUND)
                .cookie(tokenCookie)
                .cookie(nameCookie)
                .header("Location", callbackUrl)
                .build();

    }

}