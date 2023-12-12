# Swagger 사용방법. --- spring boot 3 

1. graddle

```java
implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2'
```

2. config 클래스 작성

```java
기본 패키지/config/SwaggerConfig

package back.springbootdeveloper.seungchan.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("Springdoc 테스트")
                .description("Springdoc을 사용한 Swagger UI 테스트")
                .version("1.0.0");
    }
}
```

3. Application.yml 작성

```java
springdoc:
  packages-to-scan: back.springbootdeveloper.seungchan.controller  // 패키지 이름 주의
  default-consumes-media-type: application/json;charset=UTF-8
  default-produces-media-type: application/json;charset=UTF-8
  swagger-ui:
    path: /
    disable-swagger-default-url: true
    display-request-duration: true
    operations-sorter: alpha
```

4. Controller Class 설정

```java
package back.springbootdeveloper.seungchan.controller;

// Java
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "예제 API", description = "Swagger 테스트용 API")
@RestController
@RequestMapping("/")
public class ExampleSwaggerController {
    @Operation(summary = "문자열 반복", description = "파라미터로 받은 문자열을 2번 반복합니다.")
    @Parameter(name = "str", description = "2번 반복할 문자열")
    @GetMapping("/returnStr")
    public String returnStr(@RequestParam String str) {
        return str + "\n" + str;
    }

    @GetMapping("/example")
    public String example() {
        return "예시 API";
    }

    @Hidden
    @GetMapping("/ignore")
    public String ignore() {
        return "무시되는 API";
    }
}
	
```

# Swagger 실행

```java
http://localhost:8080/v3/api-docs

{"openapi":"3.0.1","info":{"title":"Springdoc 테스트","description":"Springdoc을 사용한 Swagger UI 테스트","version":"1.0.0"},"servers":[{"url":"http://localhost:8080","description":"Generated server url"}],"paths":{},"components":{}}

http://localhost:8080/swagger-ui/index.html#/

// 리다이렉트 방법
@Controller
public class ExampleController {
	@Getmapping("/api/doc/")
    public String redirectSwagger() {
    	return "redirect:/swagger-ui/index.html";
    }
}
```

![image-20230720230012760](/Users/seungchan/Library/Application Support/typora-user-images/image-20230720230012760.png)