# 🗒️ 기능 명세표

# 해야하는것

- 리펙 (상수정리 백, 프론트)
- [ ] 디비 외래키 등록
- [ ] 비밀 번호 찾기
- [ ] 이메일 찾기 기능 추가.
- [ ] 건의 확인하기
- [ ] 스케줄러 리펙토링하기
- [ ] 예외 사항확인하기
- [ ] enumdm로 사용하기
- [ ] 로그인 한명만사용하기(실장제외)
- [ ] 배포 방법론 공부하기
- [ ] 토큰 검증이후 리다이렉트 사용
- [ ] @CrossOrigin(origins = "*", allowedHeaders = "*") 공부
- [ ] get -> null
- [ ] Update, save => 
  - [ ] @Transactional 추가. (공부)
    - [ ] import org.springframework.transaction.annotation.Transactional;

- [x] 예외처리 및 테스트 코드작성

  - [ ] https://velog.io/@wooryung/Spring-Boot-%EC%98%88%EC%99%B8-%EC%B2%98%EB%A6%AC%ED%95%98%EA%B8%B0
  - [x] Attendance Controller
    - [x] AttendanceIsPassController
    - [x] findAttendanceNumber
  - [x] Login PageController
  - [x] MainController (예외 확인)
    - [x] findAllYbUser
    - [x] findAllObUser
    - [x] fetchUserOfDetail2Main
    - [x] userControlFindInfo
    - [x] userControlPostInfo
    - [x] userExceptionAttendanceControl
  - [x] MypageController
    - [x] findMypage
    - [x] updateMyInformation
    - [x] findMypageToUpdate
  - [x] NewUserController
    - [x] findAllNewUsers
    - [x] findNewUsers
    - [x] acceptNewUserOfKing
    - [x] rejectNewUserOfKing
  - [x] SuggestionsController
    - [x] writeSuggestion
    - [x] fetchSuggestions
    - [x] findEachSuggestion
    - [x] checkSuggestions
  - [ ] TestController
  - [ ] TokenApiController
  - [ ] Vacation Page Controller (종빈)
  - [x] LookupController
    - [x] getTempPasswordController
    - [x] updatePasswordController
    - [x] updateEmailController
    - [x] findEmailController
- [ ] 토큰 시간지나면 로그아웃 하는 방법

프런트

- [ ] api url 상수로 관리하기
- [ ] 이름 변경시 모든 디비 이름 수정 (업데이트)(졍규화 이후 삭제)

[Refactor : back] 

[Feat : back] 

[Style : back] 

[Docs : back]

### 2023-01-17

#### 메모 (예외 사항)

- [ ] 건의 게시판 댓글 추가

#### Back

- [ ] 시간표 등록
- [ ] @Transactional 추가. (공부)
  - [ ] import org.springframework.transaction.annotation.Transactional;
- [x] 테스트 메서드 추가


#### Front

- [ ] 시간표 등록



### 2023-01-12

#### 메모 (예외 사항)

- [ ] 건의 게시판 댓글 추가.

#### Back

- [ ] 시간표 등록
- [ ] @Transactional 추가. (공부)
  - [ ] import org.springframework.transaction.annotation.Transactional;

- [x] MainController (예외 확인)
  - [x] rejectNewUserOfKing
- [ ] SuggestionsController
  - [x] writeSuggestion
    - [x] 검증 테스트 진행
  - [x] fetchSuggestions
  - [ ] fetchSuggestions
  - [ ] checkSuggestions


#### Front

- [ ] 시간표 등록





### 2023-01-11

#### 메모 (예외 사항)

- [ ] 건의 게시판 댓글 추가.

#### Back

- [ ] 시간표 등록
- [x] 비밀번호 변경
  - [x] 현제 비밀번호 입력을 추가하였다.
  - [x] 현제 입력한 비밀번호가 다른 것에대한 예외


#### Front

- [x] 비밀번호 변경
- [ ] 시간표 등록



### 2023-01-03

#### 메모 (예외 사항)

- [ ] 

#### Back

- [ ] 시간표 등록
- [x] 건의 게시판 
  - [x] 버튼 푸쉬 api 만들기

#### Front

- [ ] 비밀번호 변경
- [ ] 이메일 변경
- [x] 건의 게시판 (하는중)
  - [x] 버튼 푸쉬 api 없어서 만들고 다시 시작
  - [x] 읽기
  - [x] 쓰기
- [ ] 시간표 등록

### 2023-12-31

#### 메모 (예외 사항)

- [x] 2023-12-30 request 완성

#### Back

- [x] 비밀번호 변경 
  - [x] 예외처리
    - [x] PW, 검증 PW가 다르다.
      - [x] PasswordConfirmationException
  - [x] 검증
- [x] 테스트
  - [x] 일반 테스트
  - [x] 예외처리
    - [x] PW, 검증 PW가 다르다.
      - [x] PasswordConfirmationException
  - [x] 검증
- [x] 이메일 변경
  - [x] 기능
    - [x] EmailSameMatchException
    - [x] UpdateFailedException
    - [x] 검증
  
  - [x] 테스트
    - [x] EmailSameMatchException
    - [x] UpdateFailedException
    - [x] 검증
  
- [ ] 시간표 등록

#### Front

- [x] 비밀번호 찾기
- [ ] 비밀번호 변경
- [ ] 이메일 변경
- [x] 이메일 찾기
- [ ] 건의 게시판 (하는중)
- [ ] 시간표 등록

### 2023-12-29

#### 메모 (예외 사항)

#### Back

- [x] 비밀번호 찾기
  - [x] 이메일 입력하면
    - [x] request
      - [x] 이름
      - [x] 아이디을 입력
      - [x] 이메일을 입력하면
        - [x] 테스트	
          - [x] 유저가 존제하는가
            - [x] 이메일
            - [x] 이름 다를시
              - [x] UserNotExistException();
          - [x] 검증
            - [x] 이름
            - [x] 아이디
            - [x] 이메일
          - [x] 임시 비밀번호를 저장
    - [x] 임시 비밀번호를 알려주는 메일을 보낸다.
    - [x] 임시 비밀번호를 저장
  - [x] 찾을수 없다면?
    - [x] 메세지를 응답
- [x] 아이디 찾기
  - [x] 이메일을 입력하면
    - [x] 아이디를 찾는 메일을 보낸다.
- [ ] 시간표 등록

#### Front

- [ ] 비밀번호 찾기
- [ ] 아이디 찾기
- [ ] 건의 게시판
- [ ] 시간표 등록

### 2023-12-28 ~ 29

#### 메모 (예외 사항)

- [x] https://hello-judy-world.tistory.com/213

#### Back

- [x] AttendanceController#AttendanceIsPassController 예외처리
  - [x] 검증
- [x] AttendanceController#AttendanceIsPassController 테스트 코드 작성
  - [x] 기본 테스트
  - [x] 예외 사항 테스트
    - [x] 주말 여부
    - [x] 이미 휴가를 사용했다면
      - [x] 휴가를 사용할수 없다
      - [x] 중복으로 사용할 수 없다.
      - [x] 결석, 출석을 할수 없다.
    - [x] 이미 출석을 하였다면 휴가를 사용할 수 없다.
    - [x] 이미 결석을 하였다면 휴가를 사용할 수 없다.
  - [x] 검증의 테스트
- [ ] 비밀번호 찾기
- [ ] 아이디 찾기

#### Front

- [ ] 비밀번호 찾기
- [ ] 아이디 찾기

---

### 2023-12-28

#### 메모

- [x] 종빈이 스프링 부트 공부
- [x] 노션에 규칙 메모

#### Back

- [x] LoginPageController#userSignFrom 예외 처리
  - [x] requestDto 검증
  - [x] 서비스에서의 예외 처리
    - [x] 특정 유저가 저장이 되었는지
- [x] LoginPageController#userSignFrom 테스트 코드
  - [x] 신입유저가 등록후 존제하는가?
  - [x] 기본 확인 테스트 코드
    - [x] Null 검증
    - [x] NotBlank 검증
    - [x] 패턴 검증
- [ ] 비밀번호 찾기
- [ ] 아이디 찾기

#### Front

- [ ] 비밀번호 찾기
- [ ] 아이디 찾기



### 2023-12-27

#### 공통



#### Back

- [ ] 비밀번호 찾기
- [ ] 아이디 찾기

#### Front

- [ ] 비밀번호 찾기
- [ ] 아이디 찾기



### 2023-12-26

#### Back

- [x] 로그인 예외 처리
  - [x] 존제하지 않을시
  - [x] 이메일이 없을시
  - [x] 비밀번호가 없을시

- [x] 예외치리 
  - [x] 커스텀 예외처리 만들기
  - [x] 예외처리 핸들러 만들기

- [x] 테스트 함수
  - [x] 존제하지 않을시
  - [x] 이메일이 없을시
  - [x] 비밀번호가 없을시

- [x] 상수 관리
- [ ] 비밀번호 찾기
- [ ] 아이디 찾기

#### Front

- [ ] 비밀번호 찾기
- [ ] 아이디 찾기

### 2023-12-20

#### Back

- [ ] 비밀번호 찾기
- [ ] 아이디 찾기

#### Front

- [ ] 비밀번호 찾기
- [ ] 아이디 찾기

### 2023-12-19

### 공통

- [x] 일주일 단위로 출석시간 변경

#### Back

- [ ] 이름 변경시 모든 디비 이름 수정 (업데이트)
- [x] 출석 전용 아이디 만들기
- [x] 일주일 단위로 출석시간 변경

  - [x] 컨트롤러 수정
  - [x] 실제 배포후 db 확인
  - [ ] 데이터베이스 한글 변경을 한후 atttendance_time 에 이름 추가. (db)

- [x] 스케줄러 메서드 확인
  - [x] 요일별 스케줄러 메서드 체크 기능 추가.

#### Front

- [x] 업데이트(업데이트 페이지) 하고 모달 등록 및
  - [x] 2초이상 메인으로 이동
- [x] NavicationFooter 수정

  - [x] 컴포넌트화

- [x] 실장 전용 페이지

  - [x] 신입신청 게시판 링크 등록
  - [x] 출석관리 페이지 이동

- [x] 실장 전용 페이지 바닥에 마진 넣기
- [x] 신입신청 게시판 완성
- [x] 신입 신청 게시판 개인 확인 완성
- [x] next js rout받아서 관리

### 2023-12-16

#### Back

- [x] 스케쥴러 함수 추가. (수정)
  - [x] 서버 시간 변경
- [ ] 이름 변경시 모든 디비 이름 수정 (업데이트)

#### Front

- [x] 업데이트(업데이트 페이지) 하고 모달 등록 및
  - [x] 3초이상 메인으로 이동
- [x] 토큰이 있을시 (/ 에 접근 못하도록 /main으로 가도록)
  - [x] 모든 페이지 확인 및 변경
    - [x] Attendance/number/page
    - [x] Join/application/list/page (여기는 실장만 조회 가능하도록 설정)
    - [x] Login/page
    - [x] Main/page
    - [x] Mypage/page
      - [x] Update/page
    - [x] Signup/page (토큰있을시 메인화면으로 이동)
    - [x] Users/control/page (여기는 실장만 조회 가능하도록 설정)
- [x] 회원 신청 확인 페이지 url 변경
- [x] 출석번호 확인 url 변경

### 2023-12-13

#### Back

- [ ] 스케쥴러 함수 추가. (수정)
  - [ ] 서버 시간 변경
- [ ] 이름 변경시 모든 디비 이름 수정 (업데이트)

#### Front

- [x] 메인, 수정페이지 css 수정
- [x] 메인 페이지 이름 변경 적용
- [ ] 업데이트하고 모달 등록 및
  - [ ] 3초이상 메인으로 이동

### 2023-12-12

#### Back

- [ ] 결석, 휴가를 사용한 이후 덮어쓰기 금지. (front)
  - [x] 0인 경우에만 출석을 할수 있다.
    - [x] 이미 출석을 하였다면
      - [x] 출석을 수 없다.
      - [x] 휴가을 사용할수 없다.
    - [x] 이미 결석을 하였다면 d
      - [x] 출석을 할수 없다.
      - [x] 결석을 할수 없다.
    - [x] 이미 휴가를 사용했다면 결석을 할수 없다.
- [x] 중복 휴가 금지
  - [x] 이미 휴가를 사용했다면 휴가를 사용할 수 없다
  - [x] 만약 출석, 결석이 되었다면 사용금지

#### frond

- [x] 휴가를 사용할시 모달 추가. (Front)

  - [x] 휴가 중복 사용불가능
    - [x] 휴가 권한에 토큰 권한으로 데이터 얻는 front 수정

- [x] 출석하기 에 실패하면 나오는 문자 수정
- [x] 회원 등록후 모달창과 메인페이지로 이동

### 2023-12-13

#### Back

- [ ] 이미 출석을 하였다면

#### Front

- [ ]

# 느낀점

# 최종 확인

- 페이지 접근 권한 확인
