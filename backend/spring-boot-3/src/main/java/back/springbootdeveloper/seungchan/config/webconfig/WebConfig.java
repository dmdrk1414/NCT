package back.springbootdeveloper.seungchan.config.webconfig;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Bean
  public MessageSource validationMessageSource() {
    // MessageSource를 정의하는 곳에 추가적으로 validationMessageSource
    Locale.setDefault(Locale.KOREA); // 위치 한국으로 설정 (한국어로 에러 메세지 나오게)
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:/messages/validation/validation");
//        messageSource.setBasenames("classpath:message/security_message", "classpath:org/springframework/security/messages"); // 커스텀한 properties 파일, security properties 파일 순서대로 설정
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

  @Override
  public Validator getValidator() {
    // getValidator bean을 추가하였다.
    // getValidator라는 녀석은 DTO에서 messageSource를 가지고 올 수 있게 해주는 역할을 해준다.
    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
    bean.setValidationMessageSource(validationMessageSource());
    return bean;
  }

  /**
   * CORS(Cross-Origin Resource Sharing) 설정을 추가하는 메서드입니다.
   *
   * @param registry CorsRegistry 객체
   *                 <p>
   *                 CorsRegistry: CORS 설정을 등록하는 데 사용되는 클래스입니다. CORS 설정은 특정 엔드포인트에 대한 CORS 제어를 지정하는
   *                 데 사용됩니다.
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**") // 모든 엔드포인트에 대해 CORS를 적용합니다.
        .allowedHeaders("*") // 모든 헤더를 허용합니다.
        .allowedOrigins("*") // 모든 출처를 허용합니다.
        .allowedMethods("*"); // 모든 HTTP 메소드를 허용합니다.
  }
}
