INSERT INTO notification
(id, button_action, button_text, button_value, `type`, content, receiver_id,
 received_date_time, is_read)
VALUES (1, 'MOVE_LINK', '커뮤니티 확인하기', 'https://', 'EVENT', '안녕하세요', 1, now(), false);

INSERT INTO notification
(id, button_action, button_text, button_value, `type`, content, receiver_id,
 received_date_time, is_read)
VALUES (2, 'MOVE_LINK', '이벤트 참여하기', 'https://', 'EVENT', '안녕하세요', 1, now(), false);

