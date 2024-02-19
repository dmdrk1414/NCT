package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.dto.request.GoogleOAuthLoginReqDto;
import back.springbootdeveloper.seungchan.dto.request.LoginReqDto;
import back.springbootdeveloper.seungchan.dto.response.GoogleOAuthLoginResDto;
import back.springbootdeveloper.seungchan.dto.response.GoogleOAuthTokenInfoResDto;
import back.springbootdeveloper.seungchan.dto.response.UserLoginResponse;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.repository.MemberRepository;
import back.springbootdeveloper.seungchan.repository.UserRepository;
import back.springbootdeveloper.seungchan.util.GoogleConfigUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoogleOAuthLoginService {
    private final MemberRepository memberRepository;
    private final GoogleConfigUtil googleConfigUtil;
    private final ObjectMapper objectMapper;

    public void loginGoogleOAuth(LoginReqDto request){
        // Get Token from google
        // get params
        RestTemplate restTemplate = new RestTemplate(); // 외부 API 통신을 위해 사용됨.
        GoogleOAuthLoginReqDto googleOAuthLoginReqDto = GoogleOAuthLoginReqDto.builder()
                .clientId(googleConfigUtil.getGoogleClientId())
                .clientSecret(googleConfigUtil.getGoogleSecret())
                .code(request.getAuthCode())
                .redirectUri(googleConfigUtil.getGoogleRedirectUrl())
                .grantType("authorization_code")
                .build();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<GoogleOAuthLoginReqDto> httpRequestEntity = new HttpEntity<>(googleOAuthLoginReqDto, headers);
            ResponseEntity<String> apiResponseString = restTemplate.postForEntity(googleConfigUtil.getGoogleAuthUrl() + "/token", httpRequestEntity, String.class);

            // String to Object
            GoogleOAuthLoginResDto googleOAuthLoginResDto = objectMapper.readValue(apiResponseString.getBody(), new TypeReference<GoogleOAuthLoginResDto>() {});
            System.out.println(googleOAuthLoginResDto.toString());

            // Get Token Information
            String googleJwtToken = googleOAuthLoginResDto.getAccessToken();
            String requestTokenInfoUrl = UriComponentsBuilder.fromHttpUrl(googleConfigUtil.getGoogleAuthUrl() + "/tokeninfo").queryParam("access_token", googleJwtToken).toUriString();
            String resultTokenInfo = restTemplate.getForObject(requestTokenInfoUrl,String.class);
            if(resultTokenInfo != null){
                GoogleOAuthTokenInfoResDto googleOAuthTokenInfoResDto = objectMapper.readValue(resultTokenInfo, new TypeReference<GoogleOAuthTokenInfoResDto>() {});

                // Check user exist
                Optional<Member> existedMember = memberRepository.findByEmail(googleOAuthTokenInfoResDto.getEmail());
                if(existedMember.isPresent()){
                    System.out.println("User Exist: User Exist");

                }else{
                    System.out.println("User does not exist");
                }

            }else {
                // TODO : Format 지키기
                throw new Exception("Google OAuth failed!");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
