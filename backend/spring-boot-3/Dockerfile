# backend/spring-boot-3/Dockerfile
# 지금 사용중인 이미지 이름
FROM amazoncorretto:17

# 이미지 내에서 명령어를 실행할(현 위치로 잡을) 디렉토리 설정
WORKDIR /home/spring-boot-3/build

# java build 파일 jar파일 복사
COPY ./build/libs/*.jar app.jar

# 8080 포트 오픈
EXPOSE 8080

# 컨테이너 실행시 실행할 명령어
# WORKDIR에서 실행된다.
# 실행하자마자 설정이 된다.
CMD ["java", "-jar", "app.jar"]