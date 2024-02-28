package back.springbootdeveloper.seungchan.controller.config.jwt;

import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.service.MemberService;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Builder;
import lombok.Getter;
import back.springbootdeveloper.seungchan.config.jwt.JwtProperties;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static java.util.Collections.emptyMap;

@Getter
@NoArgsConstructor
public class JwtFactory {
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14); // 리프레쉬 토큰의 유효기간
    private String subject = "test@email.com";
    private Date issuedAt = new Date();
    private Date expiration = new Date(new Date().getTime() + Duration.ofDays(14).toMillis());
    private Map<String, Object> claims = emptyMap();


    /**
     * 회원에 대한 토큰을 생성합니다.
     *
     * @param member        회원 정보
     * @param jwtProperties JWT 속성
     * @return 생성된 토큰
     */
    public String createToken(Member member, JwtProperties jwtProperties) {
        Date now = new Date();
        // 토큰을 만들어 반환을 한다.
        return Jwts.builder()
                // JWT 헤더
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 typ : JWT
                // JWT 내용
                .setIssuer(jwtProperties.getIssuer()) // 내용 iss : ajufresh@gmail.com
                .setIssuedAt(now) // 내용 iat : 현재 시간
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_DURATION.toMillis())) // 내용 exp : expiry 멤버 변숫 값 / 토큰 만료기간 / 현제 + 만료 기간

                .setSubject(member.getEmail()) // 내용 sub : 유저의 이메일
                .claim("MemberId", member.getMemberId()) // 클레임 id : 유저 ID
//                .claim("isMoariumKing",) // TODO: Moarium 전체 관리자에 대한 상의가 필요
                // 서명 : 비밀값과 함께 해시값을 HS256 방식으로 암호화
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }
}
