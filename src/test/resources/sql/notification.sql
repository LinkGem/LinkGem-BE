INSERT INTO notification
(id, button_action, button_title, button_value, category, content, emoticon, title, receiver_id,
 create_date, update_date, is_read, is_deleted)
VALUES (1, 'MOVE', '커뮤니티 확인하기', 'https://', 'NOTICE', '링크잼에서 새로운 커뮤니티 서비스를 오픈했어요', '😋', '안녕하세요', 1, now(), now(),
        false, false);

INSERT INTO notification
(id, button_action, button_title, button_value, category, content, emoticon, title, receiver_id,
 create_date, update_date, is_read, is_deleted)
VALUES (2, 'MOVE', '이벤트 참여하기', 'https://', 'EVENT', '링크잼에서 새로운 이벤트를 오픈했어요', '😋', '안녕하세요', 1, now(), now(), false, false);

