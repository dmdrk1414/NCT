# nginx/Dockerfile

# docker container의 바탕이 되는 image의 이름이다.
FROM nginx:latest

# 이미지 생성 과정에서 실행할 명령어
# 이미지 생성 과정에서 npm 모듈을 설치가 된다.
# 기본 nginx 설정 파일을 삭제한다. (custom 설정과 충돌 방지)
RUN rm /etc/nginx/conf.d/default.conf

# host pc 의 nginx.conf 를 아래 경로에 복사
# Docker 이미지를 빌드할 때 파일이 컨테이너 내에 복사됩니다.
COPY ./test.nginx.conf /etc/nginx/conf.d

# 명령은 컨테이너가 시작될 때 실행됩니다.
CMD ["nginx", "-g", "daemon off;"]