package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.dto.request.LoginReqDto;
import back.springbootdeveloper.seungchan.dto.response.GoogleOAuthProfile;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.repository.MemberRepository;
import back.springbootdeveloper.seungchan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;
    private final OAuthLoginService oAuthLoginService;
    private final TokenService tokenService;

    public String loginGoogle(LoginReqDto request){
        try {
            // Google Login 요청
            GoogleOAuthProfile profile = oAuthLoginService.requestGoogleOAuthLogin(request.getAuthCode());
            String email = profile.getEmail();

            // Email 정보를 얻은 후 확인
            Optional<Member> member = memberRepository.findByEmail(email);
            if(member.isEmpty()){
                // Create new Member
                memberRepository.save(new Member(email));
                return tokenService.createAccessAndRefreshToken(email);

            }else{
                // ToDo: Check user has RefreshToken
                return null;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
