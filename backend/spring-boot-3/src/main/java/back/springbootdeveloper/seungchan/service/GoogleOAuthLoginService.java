package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.config.GoogleConfig;
import back.springbootdeveloper.seungchan.dto.request.GoogleOAuthLoginReqDto;
import back.springbootdeveloper.seungchan.dto.request.LoginReqDto;
import back.springbootdeveloper.seungchan.dto.response.GoogleOAuthLoginResDto;
import back.springbootdeveloper.seungchan.dto.response.GoogleOAuthTokenInfoResDto;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.repository.MemberRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoogleOAuthLoginService {
    private final MemberRepository memberRepository;
    private final GoogleConfig googleConfig;
    private final ObjectMapper objectMapper;

    public void loginGoogleOAuth(LoginReqDto request){
        // Get Token from google
        // get params
        RestTemplate restTemplate = new RestTemplate(); // 외부 API 통신을 위해 사용됨.
        GoogleOAuthLoginReqDto googleOAuthLoginReqDto = GoogleOAuthLoginReqDto.builder()
                .clientId(googleConfig.getGoogleClientId())
                .clientSecret(googleConfig.getGoogleSecret())
                .code(request.getAuthCode())
                .redirectUri(googleConfig.getGoogleRedirectUrl())
                .grantType("authorization_code")
                .build();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<GoogleOAuthLoginReqDto> httpRequestEntity = new HttpEntity<>(googleOAuthLoginReqDto, headers);
            ResponseEntity<String> apiResponseString = restTemplate.postForEntity(googleConfig.getGoogleTokenURI(), httpRequestEntity, String.class);

            // String to Object
            GoogleOAuthLoginResDto googleOAuthLoginResDto = objectMapper.readValue(apiResponseString.getBody(), new TypeReference<GoogleOAuthLoginResDto>() {});

            // Get Token Information(email)
            String googleAccessToken = googleOAuthLoginResDto.getAccessToken();
            String requestTokenInfoUrl = googleConfig.getGoogleTokenInfoURI(googleAccessToken);
            String resultTokenInfo = restTemplate.getForObject(requestTokenInfoUrl,String.class);

            // Check member exist
            if(resultTokenInfo != null){
                GoogleOAuthTokenInfoResDto googleOAuthTokenInfoResDto = objectMapper.readValue(resultTokenInfo, new TypeReference<GoogleOAuthTokenInfoResDto>() {});

                // Check user exist
                Optional<Member> existedMember = memberRepository.findByEmail(googleOAuthTokenInfoResDto.getEmail());
                System.out.println(existedMember.toString());
                if(existedMember.isPresent()){
                    // Check Refresh token is valid
                }else{
                    // Create Refresh, Access Token
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
