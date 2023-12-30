package back.springbootdeveloper.seungchan.domain;

import back.springbootdeveloper.seungchan.constant.regexp.RegexpConstant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TemporaryPasswordTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void 비밀번호_형식에_맞는_임시_비밀번호_생성_확인_테스트() {
        // given
        TemporaryPassword temporaryPassword = new TemporaryPassword();
        String regexPassword = RegexpConstant.PASSWORD.get();

        // when
        // then
        for (int i = 0; i < 10000; i++) {
            Boolean result = true;

            String tempPasswordString = temporaryPassword.generate();
            if (!tempPasswordString.matches(regexPassword)) {
                result = false;
            }

            assertThat(result).isTrue();
        }
    }
}