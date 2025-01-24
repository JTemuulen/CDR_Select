package mn.unitel.service;

import io.vertx.core.json.JsonObject;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestQuery;

@RegisterRestClient(baseUri = "https://sso.toki.mn/oauth2")
public interface SSOService {

    @POST
    @Path("/token")
    public JsonObject getAccessToken(@FormParam("client_id") String clientId,
                                     @FormParam("client_secret") String clientSecret,
                                     @FormParam("redirect_uri") String redirectUrl,
                                     @FormParam("code") String code,
                                     @FormParam("grant_type") String grantType);

    @GET
    @Path("/userinfo")
    public JsonObject getUserInfo(@HeaderParam("Authorization") String token, @RestQuery String userId);
}