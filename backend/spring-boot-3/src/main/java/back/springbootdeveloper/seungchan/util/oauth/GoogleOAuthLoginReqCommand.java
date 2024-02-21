package back.springbootdeveloper.seungchan.util.oauth;

import back.springbootdeveloper.seungchan.dto.request.GoogleOAuthLoginReqDto;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class GoogleOAuthLoginReqCommand implements OAuthLoginReqCommand<GoogleOAuthLoginReqDto> {

    @Value("${google.redirect.uri}")
    private String googleRedirectUrl;

    @Value("${google.client.id}")
    private String googleClientId;

    @Value("${google.secret}")
    private String googleSecret;
    @Override
    public GoogleOAuthLoginReqDto makeOAuthTokenReqBody(String authCode) {
        return GoogleOAuthLoginReqDto.builder()
                .clientId(getGoogleClientId())
                .clientSecret(getGoogleSecret())
                .code(authCode)
                .redirectUri(googleRedirectUrl)
                .grantType("authorization_code")
                .build();
    }

}
