package back.springbootdeveloper.seungchan.testutil;

import back.springbootdeveloper.seungchan.controller.config.jwt.JwtFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestCreateUtil {
    @Autowired
    private JwtFactory jwtFactory;

    public String createToken(Long memberId) {
        return jwtFactory.createToken(1L);
    }
}
