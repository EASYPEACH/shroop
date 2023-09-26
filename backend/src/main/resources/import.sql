##
계좌 비밀번호는 1234
insert into bank (id, account, name, money, password) values (1, '00293949697003', '슈룹', 0, "$2y$10$c.hfcHgbhl07CdQHk7paGuS3ufujtyFMk9ZZJKx30QI2iaj6kx19.");
insert into bank (id, account, name, password)
values (2, '00210660701016', '이종민', "$2y$10$c.hfcHgbhl07CdQHk7paGuS3ufujtyFMk9ZZJKx30QI2iaj6kx19.");
insert into bank (id, account, name, password)
values (3, '01085518532', '공태식', "$2y$10$c.hfcHgbhl07CdQHk7paGuS3ufujtyFMk9ZZJKx30QI2iaj6kx19.");
insert into bank (id, account, name, password)
values (4, '01030831889', '박진영', "$2y$10$c.hfcHgbhl07CdQHk7paGuS3ufujtyFMk9ZZJKx30QI2iaj6kx19.");
insert into bank (id, account, name, password)
values (5, '01054834790', '박지윤', "$2y$10$c.hfcHgbhl07CdQHk7paGuS3ufujtyFMk9ZZJKx30QI2iaj6kx19.");
insert into bank (id, account, name, password)
values (6, '01054834792', 'test1', "$2y$10$c.hfcHgbhl07CdQHk7paGuS3ufujtyFMk9ZZJKx30QI2iaj6kx19.");
insert into bank (id, account, name, password)
values (7, '01093949697', 'test2', "$2y$10$c.hfcHgbhl07CdQHk7paGuS3ufujtyFMk9ZZJKx30QI2iaj6kx19.");
insert into bank (id, account, name, password)
values (8, '01093949698', 'test3', "$2y$10$c.hfcHgbhl07CdQHk7paGuS3ufujtyFMk9ZZJKx30QI2iaj6kx19.");

##
admin 비밀번호는 전부 's93949697!'

insert into member (id, check_agree, create_date, login_date, login_id, nickname, password ,phone_number, point, role, update_date, account,grade_score, profile_img) values (1, 1,'2023-04-07 17:45:22','2023-04-07 17:45:22', 'shroop',  '슈룹', '$2y$10$F26pbdd5v2A3NAplkgh9YujhqcbE7Jto2emEZg516FBwapp0XGKJC', '01093949697', 0, 'ROLE_ADMIN', '2023-04-07 17:45:22', '00293949697003', 70, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/basicProfile.jpeg');

insert into member (id, check_agree, create_date, login_date, login_id, nickname, password, phone_number, point, role,
                    update_date, account, grade_score, profile_img)
values (6, 1, '2023-04-07 17:45:22', '2023-04-07 17:45:22', 'test111111', 'test111111',
        '$2y$10$tRo6AL3AMRBOXPTfqAFNBO06/2zGMTr5Cvkc5CoS0sjgnovVLCyRu', '01012345671', 100000, 'ROLE_USER',
        '2023-04-07 17:45:22', '01054834792', 60,
        'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/basicProfile.jpeg');
insert into member (id, check_agree, create_date, login_date, login_id, nickname, password, phone_number, point, role,
                    update_date, grade_score, profile_img)
values (7, 1, '2023-04-07 17:45:22', '2023-04-07 17:45:22', 'test222222', 'test222222',
        '$2y$10$nffYvzFk6vBB5J.8HFLjkuoaLmXvrtZ97Ivk4eAjWLXIUjvHc5YAe', '01012345672', 1000, 'ROLE_USER',
        '2023-04-07 17:45:22', 20, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/basicProfile.jpeg');
insert into member (id, check_agree, create_date, login_date, login_id, nickname, password, phone_number, point, role,
                    update_date, grade_score, profile_img)
values (8, 1, '2023-04-07 17:45:22', '2023-04-07 17:45:22', 'test333333', 'test333333',
        '$2y$10$xsWyO3tEhyaU79pQcHw.3OPjudF4PYrhtoqD/lKd41RscMTfffz62', '01012345673', 1000, 'ROLE_USER',
        '2023-04-07 17:45:22', 30, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/basicProfile.jpeg');
insert into member (id, check_agree, create_date, login_date, login_id, nickname, password, phone_number, point, role,
                    update_date, grade_score, profile_img)
values (9, 1, '2023-04-07 17:45:22', '2023-04-07 17:45:22', 'test444444', 'test444444',
        '$2y$10$JWTEL/Hlz93Q88V8WN7.9Os32EgsrC1vtHsKn/FUJ7CIwRC3ll6eC', '01012345674', 1000, 'ROLE_USER',
        '2023-04-07 17:45:22', 40, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/basicProfile.jpeg');
insert into member (id, check_agree, create_date, login_date, login_id, nickname, password, phone_number, point, role,
                    update_date, grade_score, profile_img)
values (10, 1, '2023-04-07 17:45:22', '2023-04-07 17:45:22', 'test555555', 'test555555',
        '$2y$10$k1UDG.quwRFX/L3yFy9Vl.gkVd4V3894gMNkLPdfIFBSgagAoDtGa', '01012345675', 2000, 'ROLE_NOT_AUTH_USER',
        '2023-04-07 17:45:22', 50, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/basicProfile.jpeg');
insert into member (id, check_agree, create_date, login_date, login_id, nickname, password, phone_number, point, role,
                    update_date, grade_score, profile_img)
values (11, 1, '2023-04-07 17:45:22', '2023-04-07 17:45:22', 'test666666', 'test666666',
        '$2y$10$D9uSnnxjF.qQJHoxMj5YbO69W5BXfW8MbajGM7X6JoVa5VikUfVC2', '01012345676', 10000, 'ROLE_DELETE',
        '2023-04-07 17:45:22', 60, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/basicProfile.jpeg');


insert into category(id, name)
values (1, '전자제품');
insert into category(id, name)
values (2, '가구');
insert into category(id, name)
values (3, '의류');
insert into category(id, name)
values (4, '티켓');
insert into category(id, name)
values (5, '도서');
insert into category(id, name)
values (6, '생활/주방');

insert into product (id, seller_id, category_id, title, product_grade, brand, price, is_checked_delivery_fee, content,
                     purchase_date, is_defect, sale_reason, likes, create_date, update_date)
values (1, 6, 1, '갤럭시 탭 S7 FE 중고 판매합니다.', 'MIDDLE', '삼성 / 갤럭시탭 S7 FE', 250000, false,
        '램 4GB 용량 64GB입니다.\n1년정도 썼고요 상태는 매우 양호합니다.\n펜이랑 필름, 케이스 다 부착되있습니다.', '2021-10-07', false,
        '다른 테블릿을 선물 받아서 이전에 사용하던 테블릿이 필요없어졌습니다.', 0, '2022-11-15 07:29:08', '2023-01-08 20:42:17');
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/6-1.webp', 1);
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/6-2.webp', 1);

insert into product (id, seller_id, category_id, title, product_grade, brand, price, is_checked_delivery_fee, content,
                     purchase_date, is_defect, sale_reason, likes, create_date, update_date)
values (2, 7, 1, '써큘레이터 선풍기 팝니다.', 'UPPER', '모름', 34000, false,
        '다이슨 구매해서 안쓰는 써큘레이터 선풍기 버리기 아까워서 처분해요.\n진짜 쓰실분 가져가시라는 의미로 소액 판매해요!', '2020-03-20', false,
        '다이슨 구매해서 안쓰는 써큘레이터 선풍기 버리기 아까워서 처분해요.', 0, '2023-07-02 09:15:25', '2022-10-04 14:34:17');
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/7-1.webp', 2);
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/7-2.webp', 2);
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/7-3.webp', 2);

insert into product (id, seller_id, category_id, title, product_grade, brand, price, is_checked_delivery_fee, content,
                     purchase_date, is_defect, sale_reason, likes, create_date, update_date)
values (3, 8, 5, '도레미곰 45권 + CD6장 + 도레미 송북 팝니다.', 'UPPER', '도레미', 90000, true,
        '풀세트입니다.\n책을 너무 늦게 들여서 별로 활용을 못해서 상태 아주아주 깨끗하고 좋아요.\n세이펜 스티커는 책 표지에 이쁘게 붙여 두었습니다.', '2022-11-07', false,
        '책을 너무 늦게 들여서 별로 활용을 못해서 팝니다.', 0, '2022-12-02 09:15:25', '2023-01-04 14:34:17');
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/8-1.webp', 3);
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/8-2.webp', 3);
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/8-3.webp', 3);
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/8-4.webp', 3);

insert into product (id, seller_id, category_id, title, product_grade, brand, price, is_checked_delivery_fee, content,
                     purchase_date, is_defect, sale_reason, likes, create_date, update_date)
values (4, 9, 2, '스토케 트립트랩 화이트 팝니다.', 'UPPER', '스토케 트립트랩', 200000, true, '스토케 트립트랩 화이트예요\n상태 좋아요\n하지만 예민하신분은 패스해주세요',
        '2023-01-23', false, '다른 의자를 사서 팝니다.', 0, '2023-04-13 09:15:25', '2023-05-07 14:34:17');
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/9-1.webp', 4);
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/9-2.webp', 4);
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/9-3.webp', 4);
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/9-4.webp', 4);
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/9-5.webp', 4);

insert into product (id, seller_id, category_id, title, product_grade, brand, price, is_checked_delivery_fee, content,
                     purchase_date, is_defect, sale_reason, likes, create_date, update_date)
values (5, 10, 1, 'lg와이드모니터 팝니다.', 'UPPER', 'lg / 와이드모니터', 30000, false,
        '사진상 내용 이구요.\n목 디스크 돌아가지\n않습니다. 새걸로 바꾸면서\n내놓습니다', '2019-05-10', true, '새걸로 바꾸면서 내놓습니다.', 0,
        '2023-06-20 09:15:25', '2023-06-21 14:34:17');
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/10-1.webp', 5);
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/10-2.webp', 5);
insert into product_img(is_defect, product_img_url, product_id)
values (true, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/10-3.webp', 5);
insert into product_img(is_defect, product_img_url, product_id)
values (true, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/10-4.webp', 5);

insert into product (id, seller_id, category_id, title, product_grade, brand, price, is_checked_delivery_fee, content,
                     purchase_date, is_defect, sale_reason, likes, create_date, update_date)
values (6, 11, 1, '다이슨 V8 카본 파이퍼 청소기 팝니다.', 'MIDDLE', '다이슨 / v8', 30000, false,
        '다이슨 V8 카본 파이퍼 청소기 팝니다\n현재 배터리는 고장난상태로 풀 충전해도 지속시간 약 5초가량입니다\n다이슨에서 배터리 교체비용 약 9.9만원으로 확인하였습니다\n(배터리 배송오면 직접 교체해야된다고 했습니다)\n구매가 약 48만원이며 구매시기 20년 12월입니다\n부속품 다 있고(3번째 사진), 다이슨 가방도 있습니다\n부속품은 아예 사용하지않아서 다 새거입니다\n본체의 흠집부분은 참고하세요~(2번째 사진)',
        '2020-12-10', true, '새걸로 바꾸면서 내놓습니다.', 0, '2023-06-02 09:15:25', '2023-06-21 14:34:17');
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/11-1.webp', 6);
insert into product_img(is_defect, product_img_url, product_id)
values (true, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/11-2.webp', 6);
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/11-3.webp', 6);
insert into product_img(is_defect, product_img_url, product_id)
values (true, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/11-4.webp', 6);
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/11-5.webp', 6);

insert into product (id, seller_id, category_id, title, product_grade, brand, price, is_checked_delivery_fee, content,
                     purchase_date, is_defect, sale_reason, likes, create_date, update_date)
values (7, 6, 1, '삼성 냉장고 255L 팝니다.', 'UPPER', '삼성 / 냉장고 255L', 150000, true,
        '가게에서 음료만 사용했습니다\n크게들어가고 깻낀데 없어요\n쿨거래 희망합니다', '2015-08-16', false, '새걸로 바꾸면서 내놓습니다.', 0, '2023-04-02 09:15:25',
        '2023-06-21 14:34:17');
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/12-1.webp', 7);
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/12-2.webp', 7);
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/12-3.webp', 7);
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/12-4.webp', 7);

insert into product (id, seller_id, category_id, title, product_grade, brand, price, is_checked_delivery_fee, content,
                     purchase_date, is_defect, sale_reason, likes, create_date, update_date)
values (8, 7, 3, '정품 나이키 바람막이 팝니다.', 'UPPER', '나이키 / 바람막이', 20000, true,
        '2022년형 여성 바람막이입니다.\n잊고 드라이를 못해 군데군데 오염이 있어요\n깨끗히 드라이하셔서 입거나 운동하실 때 편하게 입으실 분 운포로 드려요\n여자 m size 로 프리사이즈 총장 59정도입니다.',
        '2015-08-16', false, '이제 안입어서 내놓습니다.', 0, '2023-05-22 09:15:25', '2023-06-21 14:34:17');
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/16-1.webp', 8);
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/16-2.webp', 8);
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/16-3.webp', 8);
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/16-4.webp', 8);
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/16-5.webp', 8);

insert into product (id, seller_id, category_id, title, product_grade, brand, price, is_checked_delivery_fee, content,
                     purchase_date, is_defect, sale_reason, likes, create_date, update_date)
values (9, 8, 1, '삼성스마트티비 48인치 팝니다.', 'UPPER', '삼성 / 스마트티비', 60000, true,
        '*삼성 스마트티비\n48인치 사진대로 잘 나옵니다.\n다만 화면 우측 작은 섬광 나타나서 싸게 내놓아요.\n보는데 지장없지만 예민한분 피해주세요.\n(사진 3,4번 참고)\n*18일 드릴수 있습니다\n*환불불가입니다',
        '2017-03-02', true, '더 큰 티비를 샀습니다.', 0, '2023-06-01 09:15:25', '2023-06-11 14:34:17');
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/17-1.webp', 9);
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/17-2.webp', 9);
insert into product_img(is_defect, product_img_url, product_id)
values (true, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/17-3.webp', 9);
insert into product_img(is_defect, product_img_url, product_id)
values (true, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/17-4.webp', 9);
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/17-5.webp', 9);
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/17-6.webp', 9);

insert into product (id, seller_id, category_id, title, product_grade, brand, price, is_checked_delivery_fee, content,
                     purchase_date, is_defect, sale_reason, likes, create_date, update_date)
values (10, 9, 3, '에르메스 바운싱', 'UPPER', '에르메스 / 바운싱', 680000, true, '43사이즈 풀박스 오렌지 색상 바운싱 상태최고', '2023-03-02', false,
        '안신어서 샀습니다.', 0, '2023-09-08 09:15:25', '2023-09-11 14:34:17');
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/18-1.webp', 10);
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/18-2.webp', 10);

insert into product (id, seller_id, category_id, title, product_grade, brand, price, is_checked_delivery_fee, content,
                     purchase_date, is_defect, sale_reason, likes, create_date, update_date)
values (11, 10, 6, '울샴푸 새거 5리터', 'UPPER', '애경 / 울샴푸', 3000, false,
        '사진 첨부하였듯이 2019년에 구매\n뚜껑도 안딴 새거입니다\n사용 가능 기한은 잘 모르겠어요 ㅠ\n참고하셔서 구매주십시오!', '2019-12-20', false, '쓸일이 없어서 팔아요', 0,
        '2023-09-01 09:15:25', '2023-09-09 14:34:17');
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/19-1.webp', 11);
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/19-2.webp', 11);
insert into product_img(is_defect, product_img_url, product_id)
values (false, 'https://shroop-image-storage.s3.ap-northeast-2.amazonaws.com/19-3.webp', 11);

