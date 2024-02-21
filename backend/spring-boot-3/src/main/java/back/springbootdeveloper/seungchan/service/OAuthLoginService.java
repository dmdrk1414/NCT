package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.dto.request.LoginReqDto;
import back.springbootdeveloper.seungchan.dto.response.GoogleOAuthProfile;
import back.springbootdeveloper.seungchan.util.oauth.GoogleOAuthLoginApiClient;
import back.springbootdeveloper.seungchan.util.oauth.OAuthLoginApiClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public class OAuthLoginService {
    public GoogleOAuthProfile requestGoogleOAuthLogin(String authCode){
        OAuthLoginApiClient<GoogleOAuthProfile> client = new GoogleOAuthLoginApiClient();
        try {
            String accessToken = client.requestOAuthClientAccessToken(authCode);
            return client.requestOauthInfo(accessToken);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
