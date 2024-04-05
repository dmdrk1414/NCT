# NCT

<img src='https://user-images.githubusercontent.com/76943741/273447079-732127ea-59ba-404a-ae27-0bb963199c1a.png' width="40%" height="40%">    

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
9. 실행방법
10. 느낀점

## 1. 📺 서비스 소개

## 1.1 초기 화면

<img src="https://github.com/dmdrk1414/NCT/assets/76943741/6bdc538c-d42b-4303-8634-32bf9d2cb5a4" alt="초기화면" style="zoom:50%;" />

## 1.2 메인 페이지

<img src="https://github.com/dmdrk1414/NCT/assets/76943741/669e553b-313f-498f-9cac-b6137174c3e5" alt="메인페이지" style="zoom:50%;" />

## 1.3 실장 페이지

<img src="https://github.com/dmdrk1414/NCT/assets/76943741/18f3d9aa-c57b-49a9-8df4-f0000d7087b9" alt="실장_페이지" style="zoom:50%;" />

## 2. 🔎 서비스 개요

동아리 명 : 누리 고시원

효율적인 동아리원들의 관리를 하기 위한 웹앱 어플리 케이션

동아리 친구들의 불편함을 없애주기위한 앱플리케이션이다.



## 3. 🛠️ 주요 기능 소개 (추후 계속 업데이트)

- 출석 기능 : 매일 원하는 시간에 맞는 출석을 할수있다.
- 신입 회원의 동아리 지원 : 신입 회원들의 동아리를 지원을 할 수 있는 폼을 제공
- 휴가 신청 : 금일 휴가 신청가능
- 개별 출석시간, 장기휴가 신청 : 개별적인 출석시간과 장기 휴가 신청을 지정할 수 있다.



## 4. 🔧 기술 스택



## 5. 📦 아키텍처

<img src='https://user-images.githubusercontent.com/76943741/273447123-237d4516-50a4-4d43-a1f1-05e01ddd0a9b.png' width="80%" height="100%">

## 6. 🕸️ ERD

<img src='https://user-images.githubusercontent.com/76943741/273447236-920471b6-ec62-4188-a74d-0f01287d7d58.png' width="80%" height="100%">

<img src='https://user-images.githubusercontent.com/76943741/273447255-cb121191-cfce-44ca-a3af-88a5320bf5da.png' width="50%" height="100%">



## 7. 💻 피그마

<img src='https://user-images.githubusercontent.com/76943741/273447277-bf31ab16-9555-46c2-8e7a-55176aa9e4e8.png' width="90%" height="100%">

## 8. API 명세표

**노션**

https://oceanic-tenor-b93.notion.site/API-fae70f6d5a724e1fa3cff3d5406bd2d4

<img src='https://user-images.githubusercontent.com/76943741/273447196-0e33a36d-2df8-406f-beb1-d4ce917eeda2.png' width="80%" height="100%">

**스웨거 (spring boot 실행이후)**

http://localhost:8080/v3/api-docs
*http://localhost:8080/swagger-ui/index.html*

<img src='https://user-images.githubusercontent.com/76943741/273447224-e38e0877-d61e-4d1f-bd9c-0ed54b64d0c0.png' width="80%" height="100%">

---

# 9 실행 방법

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



## 10 느낀점🤔 개발을 하며 적었습니다.

1. 서비스 흐름을 제대로 잡자

   서비스 흐름을 제대로 잡지 않으니 수정하는 일이 너무 많았다.

   저는 처음에 서비스 흐름이 왜 중요한지 몰랐습니다. 하지만 설계 단계에서 서비스 흐름을 잘잡지 않고 작업을 진행을 하여

   수정하는 일이 너무 많아 힘들었습니다.

2. 디비 설계는 공부를 해서라도 천천히 하자

   가장 어려운 것이 디비 설계입니다... 저의 경험의 부재를 느끼며 작업을 하고있습니다.

3. front와 back을 합칠때 제가 front친구들에게 테스트 db와 sql문을 줘야겠다는 생각을 하였습니다.

   디비의 설정이 힘들지만 왜 test 디비를 줘야되는 이유를 알게 되어 좋았습니다.

4. 문자열을 변수로 저장하라.

5. 아침마다 얼마나 했는지 어디를 했는지 서로 확인하는 과정의 중요성

6. 테스트 디비를 꼭연결하자 어디든, 누구든 테스트를 할수있도록

   1. 테스트 데이터는 5개이상 넣자

7. 변수이름, respond json 변수명은 규칙대로 실행하자.

8. 자바버전확인 꼭!!!! 제발 이상한 버전 쓰지말고 생각 많이 하고 정하기

9. 비동기 문제 해결을 위한 값의 변경은 useState으로 해결하라.

   setAllertModalStatus(1);가 즉시 AllertModalstatus 상태를 1로 변경하지 않는 이유는 React의 상태 업데이트가 비동기적으로 작동하기 때문입니다. 즉, setAllertModalStatus(1)를 호출한 직후에 AllertModalstatus 상태를 확인하면, 아직 업데이트가 반영되지 않았을 수 있습니다.

   React의 useState 훅은 상태를 즉시 업데이트하지 않습니다. 대신, React는 상태 업데이트를 예약하고, 컴포넌트를 비동기적으로 재렌더링합니다. 이로 인해 setAllertModalStatus(1)를 호출한 직후에 AllertModalstatus를 확인하면 이전 상태값을 볼 수 있습니다.


배포할시

https://github.com/dmdrk1414?tab=repositories

https://github.com/dmdrk1414/docker-test

1. 서버 컴퓨터의 사양을 체크하자.
2. 도커 컨테이너의 이름을 지정하자
   1. compose을 사용할시 랜덤으로 폴더이름으로 변경이 되는 점이 찾기 힘들다.
      1. 왜냐하면 nginx을 할때도 컨테이너의 이름으로 리버싱 프록시를 하기 때문이다.
3. 도커 파일을 설정할때 생각을 많이 하고 설정하자 설정파일을 깃에 올릴테니 확인하고 설정

4. 버전 관리 툴의 소중함 nvm 

5. aws의 소중함. 이런 귀찮음을 해결해주는 aws에게 감사하자
6. mysql의 접근 ip의 다양함을 생각하자.
7. mysql
   1. 한글 설정



```
INSERT INTO user_info VALUES    (4.2, 'ENTP', 'dong',
        'advantege',
        '1996',
        'false',
        'favorite', 'computer', 'name', '010-1234-1234', 'image',
        'self-introduce',
        '1234', '123', TRUE, '', 'view@gmail.com', '$2a$10$5tb6qszu4wtEmbQHZDKVPutSiqjPB.sisziAFHcWXXwo2akedGpxK',
        TRUE);
```

