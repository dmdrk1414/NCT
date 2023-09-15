INSERT INTO USER_INFO (gpa,
                       mbti,
                       address,
                       advantages,
                       birth_date,
                       disadvantage,
                       hobby,
                       major,
                       name,
                       phone_num,
                       photo,
                       self_introduction,
                       specialty_skill,
                       student_id,
                       ob,
                       year_registration,
                       email,
                       password,
                       regular_member)
VALUES (4.2, 'ENTP', '수완동', '나의 장점은', '1996-04-15', '나의 단점', '달리기', '컴퓨터 공학과', '박승찬', '010-2383-6578', '사진', '자기소개',
        '특기', '20161822', FALSE, '2023', 'seungchan141414@gmail.com',
        '$2a$10$735DgTje5HKqKqQrD3Jd5.j4sfdDF5Q4aXx7TUq2JWYwj/pz3n05a', TRUE),
       (4.2, 'ENTP', '수완동', '나의 장점은', '1996-04-15', '나의 단점', '달리기', '컴퓨터 공학과', '이승훈', '010-2383-6578', '사진', '자기소개',
        '특기', '20161822', TRUE, '2023', '2@gmail.com', '$2a$10$5tb6qszu4wtEmbQHZDKVPutSiqjPB.sisziAFHcWXXwo2akedGpxK',
        TRUE),
       (4.2, 'ENTP', '수완동', '나의 장점은', '1996-04-15', '나의 단점', '달리기', '컴퓨터 공학과', '김주연', '010-2383-6578', '사진', '자기소개',
        '특기', '20161822', FALSE, '2023', '3@gmail.com', '$2a$10$5tb6qszu4wtEmbQHZDKVPutSiqjPB.sisziAFHcWXXwo2akedGpxK',
        TRUE),
       (4.2, 'ENTP', '수완동', '나의 장점은', '1996-04-15', '나의 단점', '달리기', '컴퓨터 공학과', '이동근', '010-2383-6578', '사진', '자기소개',
        '특기', '20161822', TRUE, '2023', '4@gmail.com', '$2a$10$5tb6qszu4wtEmbQHZDKVPutSiqjPB.sisziAFHcWXXwo2akedGpxK',
        TRUE),
       (4.2, 'ENTP', '수완동', '나의 장점은', '1996-04-15', '나의 단점', '달리기', '컴퓨터 공학과', '허진범', '010-2383-6578', '사진', '자기소개',
        '특기', '20161822', FALSE, '2023', '5@gmail.com', '$2a$10$5tb6qszu4wtEmbQHZDKVPutSiqjPB.sisziAFHcWXXwo2akedGpxK',
        TRUE);



INSERT INTO user_utill (id, cnt_vacation, nuri_king, name, user_id, general_affairs)
VALUES (1, 0, TRUE, '박승찬', 1, FALSE),
       (2, 1, FALSE, '김주연', 3, FALSE),
       (3, 1, FALSE, '허진범', 5, TRUE);

INSERT INTO suggestions (id, classification, check_content, title, holiday_period)
VALUES (1, '건의', TRUE, '건의 제목 1', ''),
       (2, '휴가', FALSE, '휴가 제목 1', '2023-08-01 ~ 2023-08-07'),
       (3, '자유', TRUE, '자유 제목 1', ''),
       (4, '비밀', FALSE, '비밀 제목 1', '');

INSERT INTO attendance_status (id, absence_dates, name, user_id, vacation_dates, weekly_data)
VALUES (1, '2023-08-15', '박승찬', 1, '2023-08-01, 2023-08-07, 2023-08-14', '[0,0,0,0,0]'),
       (2, '2023-08-11', '김주연', 3, '2023-08-08, 2023-08-18', '[0,0,0,0,0]'),
       (3, '2023-08-18', '허진범', 5, '2023-08-07, 2023-08-11', '[1,0,0,0,0]');

INSERT INTO temp_user (id,
                       gpa,
                       mbti,
                       address,
                       advantages,
                       birth_date,
                       disadvantage,
                       hobby,
                       major,
                       name,
                       phone_num,
                       photo,
                       self_introduction,
                       specialty_skill,
                       student_id,
                       ob,
                       year_registration,
                       email,
                       password,
                       regular_member,
                       application_date)
VALUES (1, 4.2, 'ENTP', '수완동', '나의 장점은', '1996-04-15', '나의 단점', '달리기', '컴퓨터 공학과', '새로운신입_1', '010-2383-6578', '사진',
        '자기소개',
        '특기', '20161822', FALSE, '2023', 'new1@gmail.com',
        '$2a$10$735DgTje5HKqKqQrD3Jd5.j4sfdDF5Q4aXx7TUq2JWYwj/pz3n05a', FALSE, '2023-08-21'),
       (2, 4.2, 'ENTP', '수완동', '나의 장점은', '1996-04-15', '나의 단점', '달리기', '컴퓨터 공학과', '새로운신입_2', '010-2383-6578', '사진',
        '자기소개',
        '특기', '20161822', FALSE, '2023', 'new2@gmail.com',
        '$2a$10$735DgTje5HKqKqQrD3Jd5.j4sfdDF5Q4aXx7TUq2JWYwj/pz3n05a', FALSE, '2023-08-22');

INSERT INTO PERIODIC_DATA (NAME, PREVIOUS_MONTH, THIS_MONTH, USER_ID, WEEKLY_DATA)
VALUES ('박승찬', '1:[1,0,1,1,1] , 2:[0,1,1,1,1] , 3:[0,-1,1,1,1] , 4:[0,0,0,0,0] , ', 6, 1, '[0,0,0,0,0]'),
       ('김주연', '1:[1,1,1,1,1] , 2:[1,0,1,1,-1] , 3:[1,1,1,1,0] , 4:[0,0,0,0,0] , ', 6, 3, '[0,0,0,0,0]'),
       ('허진범', '1:[1,1,1,1,1] , 2:[0,1,1,1,0] , 3:[1,1,1,1,-1] , 4:[0,0,0,0,0] , ', 6, 5, '[1,0,0,0,0]');

INSERT INTO num_of_today_attendance (check_num, today)
VALUES ('1234', '2023-09-07'),
       ('2345', '2023-09-08');


INSERT INTO attendance_time (attendance_time, name, user_id)
VALUES ('09', '박승찬', 1),
       ('09', '김주연', 3),
       ('09', '허진범', 5);
