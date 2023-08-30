CREATE TABLE IF NOT EXISTS num_of_today_attendance
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    check_num VARCHAR(20) NOT NULL,
    `day`     VARCHAR(20) NOT NULL
);