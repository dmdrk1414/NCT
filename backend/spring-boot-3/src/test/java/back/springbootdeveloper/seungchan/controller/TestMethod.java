package back.springbootdeveloper.seungchan.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class TestMethod {
    @DisplayName("test_1")
    @Test
    public void test_1() throws Exception {
        assertThat(1).isEqualTo(1);
    }
}
