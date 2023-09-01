# database/mysql/Dockerfile
# 기반 이미지로 MySQL 공식 이미지 사용
FROM mysql:8.0.34

# 환경 변수 설정
ENV MYSQL_ROOT_PASSWORD=nuri777!
ENV MYSQL_DATABASE=nct_db
ENV MYSQL_ROOT_HOST=root
ENV TZ=Asia/Seoul

# Docker 이미지를 빌드할 때 파일이 컨테이너 내에 복사됩니다.
# 초기 디비 구성 
COPY ./scripts/ /docker-entrypoint-initdb.d/
COPY ./my.cnf /etc/my.cnf

# 명령은 컨테이너가 시작될 때 실행됩니다.
CMD ["mysqld", "--character-set-server=utf8mb4", "--collation-server=utf8mb4_unicode_ci"]

# 컨테이너 포트 설정
EXPOSE 3306