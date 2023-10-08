# NCT



<img src='https://github.com/dmdrk1414/NCT/issues/61'>    

##  Nuri Control Tower

## 🕛 기간

2023.07.22 ~



## :memo: 목차

1. 서비스 소개(화면)
2. 서비스 개요
3. 주요 기능 소개
4. 기술 스택
5. 아키텍처
6. ERD
7. 피그마
8. api 명세표

## 1. 📺 서비스 소개 (화면)



## 2. 🔎 서비스 개요

동아리 명 : 누리 고시원

효율적인 동아리원들의 관리를 하기 위한 웹앱 어플리 케이션



## 3. 🛠️ 주요 기능 소개 (추후 계속 업데이트)

- 출석 기능 : 매일 원하는 시간에 맞는 출석을 할수있다.
- 신입 회원의 동아리 지원 : 신입 회원들의 동아리를 지원을 할 수 있는 폼을 제공
- 휴가 신청 : 금일 휴가 신청가능
- 개별 출석시간, 장기휴가 신청 : 개별적인 출석시간과 장기 휴가 신청을 지정할 수 있다.



## 4. 🔧 기술 스택



## 5. 📦 아키텍처



## 6. 🕸️ ERD



## 7. 💻 피그마



## 8. API 명세표

**노션**

https://oceanic-tenor-b93.notion.site/API-fae70f6d5a724e1fa3cff3d5406bd2d4



**스웨거 (spring boot 실행이후)**

http://localhost:8080/v3/api-docs
*http://localhost:8080/swagger-ui/index.html*



---

# 실행 방법

```python
# git 레파지토리 클론
git clone https://github.com/dmdrk1414/NCT.git

# 클론한 레파지토리 폴더 이동
cd NCT

# NCT/frontend 폴더이동
cd frontend

# npm을 이용한 관련 패키지 설치
npm i

# 개발 전용 웹사이트 실행
npm run dev	

# 스프링부트에서 run을 해야 결과 보인다.
```





신입신청을 한후 신청 리스트 확인
http://localhost:3000/join-application-list

출석을 위한 번호  url
http://localhost:3000/attendance-number 

나의 정보 수정
http://localhost:3000/mypage/update

