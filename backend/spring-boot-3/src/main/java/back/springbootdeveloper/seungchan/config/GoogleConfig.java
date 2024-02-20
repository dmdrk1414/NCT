package back.springbootdeveloper.seungchan.config;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Data
public class GoogleConfig {
    @Value("${google.auth.url}")
    private String googleAuthUrl;

    @Value("${google.login.url}")
    private String googleLoginUrl;

    @Value("${google.redirect.uri}")
    private String googleRedirectUrl;

    @Value("${google.client.id}")
    private String googleClientId;

    @Value("${google.secret}")
    private String googleSecret;

    @Value("${google.auth.scope}")
    private String scopes;

    public String getGoogleTokenURI(){
        return getGoogleAuthUrl() + "/token";
    }

    public String getGoogleTokenInfoURI(String jwtAccessToken){
        return UriComponentsBuilder.fromHttpUrl(getGoogleAuthUrl() + "/tokeninfo").queryParam("access_token", jwtAccessToken).toUriString();
    }

}
