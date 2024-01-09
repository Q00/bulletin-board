

INSERT INTO USER (ID, EMAIL, PASSWORD,  REG_DATE,  USER_NAME)
VALUES (1, 'admin@naver.com', '{bcrypt}$2a$10$JnisKHEJGNFISClc5k6ivO1QxQdBe8Cs7NRBMcLa45aWaBlPvS7mm',  '2023-02-01 00:49:43.000000', 'alex')
     , (2, 'test2@kakao.com', '{bcrypt}$2a$10$JnisKHEJGNFISClc5k6ivO1QxQdBe8Cs7NRBMcLa45aWaBlPvS7mm', '2023-02-19 00:50:11.000000', 'jacob')
     , (3, 'test3@gmail.com', '{bcrypt}$2a$10$JnisKHEJGNFISClc5k6ivO1QxQdBe8Cs7NRBMcLa45aWaBlPvS7mm','2024-02-19 23:27:07.000000', 'jeff')
     , (4, 'test4@gmail.com', '{bcrypt}$2a$10$JnisKHEJGNFISClc5k6ivO1QxQdBe8Cs7NRBMcLa45aWaBlPvS7mm', '2024-02-02 00:27:51.000000', 'blue')
;

INSERT INTO USER_ROLE (USER_ID, ROLE)
VALUES (1, 'ADMIN')
     , (2, 'NORMAL')
     , (3, 'NORMAL')
     , (4, 'NORMAL');


INSERT INTO BOARD (ID, USER_ID, TITLE, CONTENTS, CREATED_AT)
VALUES (1, 1, '게시글1', '게시글내용1', '2021-02-01 01:12:37.000000')
     , (2, 2, '게시글2', '게시글내용2', '2021-02-01 01:12:37.000000')
     , (3, 3, '문의제목1', '문의내용1', '2021-02-01 01:12:37.000000')
;
