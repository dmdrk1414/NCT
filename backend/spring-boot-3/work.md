# 기능 구현

- [x] 회원가입 지원서 작성 api
  - [x] controller
  - [x] requestdto
  - [x] service

- [x]  건의 게시판 조회
  - [x] controller
  - [x] responsedto
  - [x] service
  
- [x]  Suggestion artile write
  
  - [x] Holliday  컬럼, 변수 추가
  - [x] controller
  - [x] responsedto
  - [x] service
  
- [ ]   3-4 메인 - 회원상세(일반, 실장)
  
  - [x] Controller 의 서비스 주입
    - [x] 유저의 현제 정보를 가져와서 식별
  - [x] 서비스 
    - [x] UserUtil dB에서 정보를 가져와야된다.
  - [x] 유저의 디테일한 response을 위한 dto
  - [x] User Still DB 만듬
  - [x] User의 레파지토리 만들었다.
  
- [ ]  attendance_status , 1. 메인-출결 현황 페이지 만들기
  
  - [x] 엔티티, db새성
  
    - [x] mysql> SELECT * FROM attendance_status;
      +----+---------------+-----------+---------+----------------+
      | id | absence_dates | name      | user_id | vacation_dates |
      +----+---------------+-----------+---------+----------------+
      |  1 | 33~##         | 박승찬    |       2 | 11~11          |
      +----+---------------+-----------+---------+----------------+
  
  - [x] repository 
  
  - [x] 컴트롤러
  
  - [x] dto
  
  - [x] service
  
  - [ ] user와 테이블 좋게 만들기 테스트하기 편하게
  
    - [x] User
    - [x] User_utill
    - [x] suggestions
    - [x] Attendance_status
  
  - [ ] 5. 출석 번호 입력 API 
  
    6. [x] controller
  
    7. [x] service
  
      1. [x] 데이터베이스에서 출석번호와 맞는지 확인
  
      2. [x] 어디에 저장해야될지 생각중
  
      3. [x] Today_attandence_num 만들기
  
      4. [x] DB의 day문자열에서 year, month, day을 얻는다.
  
      5. [x] AttendanceService와 NumOfTodayAttenens을 연결하라
  
      6. [x] 일단 서비스에서 멈춤
  
    ​    
  
  - [x] \3. 메인 - 회원상세(일반, 실장), 8. 졸업자 상세 페이지 (실장)
  
    - [x] YB의 상세정보와 합쳤다, 따로 나누지 않고 
    - [x] 한번에 수정
  
  - [x]  6,7 메인-졸업자 출력 페이지 (일반, 실장) ob 추가
  
    - [x] 컨트롤러
    - [x] 서비스
      - [x] oBUser을 찾는 방법
    - [x] 도메인
      - [x] ObUser의 정보 따로 저장
  
  - [x] 로그인 
  
    - [x] config
    - [x] controller
    - [x] domain
    - [x] dto
    - [x] repository

2. 휴가 신청 API 할시 휴가가 계속 (휴가 일정이 없어도 휴가가 사라진다.)
3. 한달마다 휴가 db 리셋

토큰 생성시 

1.  3-4 메인 - 회원상세(일반, 실장) 토큰 추가
2. responstBodu refactory
3. Attendance Controller  have to take userId from token
   1. 토큰 생기면 변경 필요
4. MainControler -> findMypage에서 토큰을 갱신
5. VacationCountController 실장인지 아닌지 확인



해야되는거

attendanceService 토요일 일요일은 출석이 안되도록 수정

매일매일 출석 체크가 자동으로 되도록 수정

회원 가입을 할때 db생성이 잘되는지 확인

1. Table join 하기 
