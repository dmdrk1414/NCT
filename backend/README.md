# 느낌점

1. 서비스 흐름을 제대로 잡자

   서비스 흐름을 제대로 잡지 않으니 수정하는 일이 너무 많았다.

   저는 처음에 서비스 흐름이 왜 중요한지 몰랐습니다. 하지만 설계 단계에서 서비스 흐름을 잘잡지 않고 작업을 진행을 하여

   수정하는 일이 너무 많아 힘들었습니다.

2. 디비 설계는 공부를 해서라도 천천히 하자

   가장 어려운 것이 디비 설계입니다... 저의 경험의 부재를 느끼며 작업을 하고있습니다.

3. front와 back을 합칠때 제가 front친구들에게 테스트 db와 sql문을 줘야겠다는 생각을 하였습니다.

   디비의 설정이 힘들지만 왜 test 디비를 줘야되는 이유를 알게 되어 좋았습니다.

# spring boot 3.0.2 

```java
// build.gradle 
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.0.2' // 스프링 부트 플러그인
    id 'io.spring.dependency-management' version '1.1.0'  // 의존성 관리, 스프링의 의존성 관리 플러그임
}

group = 'back.springbootdeveloper'
version = '1.0-SNAPSHOT'
sourceCompatibility = '17' //  자바 소스를 컴파일할 때 사용할 자바 버전을 입력

repositories {
    mavenCentral()
}

dependencies {
    // Spring MVC를 사용해서 RESTful 웹 서비스를 개발할때 필요한 의존성 모음
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-security'

    // 스프링 애플리케이션을 테스트하기 위해 필요한 의존성 모음
    testImplementation 'org.springframework.boot:spring-boot-starter-test' // 테스트 기능

    // 반복 메서드 작성 작업을 줄여준다.
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'

    //  스프링 데이터 JPA
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'

    implementation 'mysql:mysql-connector-java:8.0.27'

    // str <==> json parsing method
    implementation 'org.json:json:20200518'

    // 8장 추가 스프링 시큐리티를 사용하기 위한 추가
    // 스프링 시큐리티를 사용하기 위한 스타터 추가
    implementation 'org.springframework.boot:spring-boot-starter-security:3.0.2'

    // 스프링 시큐리티를 테스트하기 위한 의존성 추가
    testImplementation 'org.springframework.security:spring-security-test'


    //프로젝트에 jwt 라이브러리를 추가하여 JSON Web Token(JWT)을 생성하고
    // 처리하는 기능을 사용할 수 있도록 해줍니다.
    // JWT는 인증 및 인가를 위한 토큰 기반 인증 방식으로 자주 사용되며,
    // jjwt는 JWT를 쉽게 생성하고 구문 분석하고 검증할 수 있는 Java 라이브러리입니다
    implementation 'io.jsonwebtoken:jjwt:0.9.1' // // 자바 JWT 라이브러리
    implementation 'javax.xml.bind:jaxb-api:2.3.1' // XML 문서와 Java 객체 간 매핑을 자동화
}

test {
    useJUnitPlatform()
}
```

# doker - mysql db 연결

```java
# doker 설정

// 초기 도커 시작
// 초기 도커 시작하면서 DataBase 생성하기.
docker run -d -p 3306:3306 --name NCT-container -e MYSQL_DATABASE=NCT-DB -e MYSQL_USER=dmdrk1414 -e MYSQL_PASSWORD=qkrtmdcks1! -e MYSQL_ROOT_PASSWORD=qkrtmdcks1! mysql:latest

// ~/.zshrc 설정
# mysql setting
alias cdmysql="cd /opt/homebrew/opt/mysql@8.0/bin"
alias runmysql="./mysql -h 127.0.0.01 -P 3306 -u root -p"

// docker 실행 셀 스크립트 설정
# docker config
alias docker="/Applications/Docker.app/Contents/Resources/bin/docker"
```



```java
# mysql 설정  mysql@8.0@
# application.properties
# db 연결

spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/NCT-DB
spring.datasource.username=root
spring.datasource.password=qkrtmdcks1!
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

```java
# build.gradle

    //  스프링 데이터 JPA
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'

    implementation 'mysql:mysql-connector-java:8.0.27'
```

# Swagger

https://dmdrk1414.tistory.com/51



```java
# build.gradle		

implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2' // spring boot 3.0.2 버전만
```

1. config 클래스 작성

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

2. Application.yml 작성

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

3. Controller Class 설정

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

4. 실행 방법

```java
// 기본 실행 url
// http://localhost:8080/swagger-ui/index.html#/
 
 // 리다이렉트 방법
 @Controller
 public class ExampleController {
   @Getmapping("/api/doc/")
     public String redirectSwagger() {
       return "redirect:/swagger-ui/index.html";
     }
 }
```

