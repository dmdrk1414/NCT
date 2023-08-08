package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.config.jwt.TokenProvider;
import back.springbootdeveloper.seungchan.domain.RefreshToken;
import back.springbootdeveloper.seungchan.domain.User;
import back.springbootdeveloper.seungchan.repository.RefreshTokenRepository;
import back.springbootdeveloper.seungchan.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class TokenService {
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token"; // 리프레쉬 토큰의 이름
    public static final String ACCESS_TOKEN_COOKIE_NAME = "access_token"; // 리프레쉬 토큰의 이름
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14); // 리프레쉬 토큰의 유효기간
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1); // 액세스 토큰의 유효기간
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    public String createNewAccessToken(String refreshToken) {
        // 토큰 유효성 검사에 실패하면 예외 발생
        if (!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findUserById(userId);

        return tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
    }

    public String createAccessAndRefreshToken(HttpServletRequest request, HttpServletResponse response, String userEmail) {
        // 이메일을 기반으로 사용자 정보를 조회
        User user = userService.findByEmail(userEmail);

        // 새로운 리프레쉬 토큰을 생성한다.
        String refreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);

        // 리프레쉬 토큰을 db에 저장한다.
        saveRefreshToken(user.getId(), refreshToken);

        // 리프레쉬 토큰을 쿠키에 추가한다.
        addRefreshTokenToCookie(request, response, refreshToken);

        // 새로운 access 토큰을 생성한다.
        String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);


        return accessToken;
    }


    // 사용자 ID를 기반으로 refresh 토큰을 저장하거나 갱신한다.
    private void saveRefreshToken(Long userId, String newRefreshToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
                .map(entity -> entity.update(newRefreshToken))
                .orElse(new RefreshToken(userId, newRefreshToken));

        refreshTokenRepository.save(refreshToken);
    }

    // refresh 토큰을 쿠키에 추가한다.
    private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();

        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
    }

    public Long getUserIdFromToken(String token) {
        return tokenProvider.getUserId(token);
    }
}
