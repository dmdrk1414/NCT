package back.springbootdeveloper.seungchan.util.oauth;

import back.springbootdeveloper.seungchan.dto.response.GoogleOAuthProfile;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface OAuthLoginApiClient<P> {
    public String getTokenURI();

    public String requestOAuthClientAccessToken(String authCode) throws JsonProcessingException;
    public P requestOauthInfo(String accessToken) throws JsonProcessingException;

}
