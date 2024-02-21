package back.springbootdeveloper.seungchan.util.oauth;

import back.springbootdeveloper.seungchan.dto.request.GoogleOAuthLoginReqDto;
import back.springbootdeveloper.seungchan.dto.response.GoogleOAuthLoginResDto;
import back.springbootdeveloper.seungchan.dto.response.GoogleOAuthProfile;
import back.springbootdeveloper.seungchan.dto.response.GoogleOAuthTokenInfoResDto;
import back.springbootdeveloper.seungchan.filter.handler.RestTemplateResponseErrorHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Data
public class GoogleOAuthLoginApiClient implements OAuthLoginApiClient<GoogleOAuthProfile>{
    @Value("${google.auth.url}")
    private String googleAuthUrl;

    @Value("${google.login.url}")
    private String googleLoginUrl;

    @Value("${google.auth.scope}")
    private String scopes;

    private RestTemplate restTemplate = new RestTemplateBuilder().errorHandler(new RestTemplateResponseErrorHandler()).build();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String getTokenURI() {
        return getGoogleAuthUrl() + "/token";
    }

    public String getGoogleTokenInfoURI(String accessToken){
        return UriComponentsBuilder.fromHttpUrl(getGoogleAuthUrl() + "/tokeninfo").queryParam("access_token", accessToken).toUriString();
    }


    @Override
    public String requestOAuthClientAccessToken(String authCode) throws JsonProcessingException {
        // Make Request
        GoogleOAuthLoginReqCommand command = new GoogleOAuthLoginReqCommand();
        GoogleOAuthLoginReqDto request= command.makeOAuthTokenReqBody(authCode);

        // Request /token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<GoogleOAuthLoginReqDto> httpRequestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<String> apiResponseString = restTemplate.postForEntity(getTokenURI(), httpRequestEntity, String.class);
        GoogleOAuthLoginResDto googleOAuthLoginResDto = objectMapper.readValue(apiResponseString.getBody(), new TypeReference<GoogleOAuthLoginResDto>() {});

        return googleOAuthLoginResDto.getAccessToken();
    }

    @Override
    public GoogleOAuthProfile requestOauthInfo(String accessToken) throws JsonProcessingException {
        String requestTokenInfoUrl = getGoogleTokenInfoURI(accessToken);
        String resultTokenInfo = restTemplate.getForObject(requestTokenInfoUrl,String.class);

        // Check member exist
        if(resultTokenInfo == null) {
            // TODO Exception : Email Information does not exist
            System.out.println("Does not exist");
        }
        GoogleOAuthTokenInfoResDto response = objectMapper.readValue(resultTokenInfo, new TypeReference<GoogleOAuthTokenInfoResDto>() {});
        return new GoogleOAuthProfile(response.getEmail());
    }


}
