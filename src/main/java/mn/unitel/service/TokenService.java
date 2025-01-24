package mn.unitel.service;

import org.eclipse.microprofile.jwt.Claims;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.HashSet;

@ApplicationScoped
public class TokenService {

    public String generateToken(String userId) {

        String token = Jwt.issuer("https://example.com/issuer")
                .upn(userId)
                .expiresIn(3600)
                .claim(Claims.sub.name(), userId)
                .groups(new HashSet<>(Arrays.asList("User")))
                .sign();

        return token;
    }


}