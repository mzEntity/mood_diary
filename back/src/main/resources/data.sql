INSERT INTO `mood` (id, name)
VALUES (1, '开心'),
       (2, '期待'),
       (3, '难过'),
       (4, '懊恼'),
       (5, '平静'),
       (6, '幸福'),
       (7, '后悔');

INSERT INTO `request_state` (id, name)
VALUES (1, '待审核'),
       (2, '已通过'),
       (3, '已拒绝');

INSERT INTO `user` (id, username, password)
VALUES (1, 'Mike', 'password'),
       (2, 'Jane', 'password'),
       (3, 'Jack', 'password'),
       (4, 'Bill', 'password');

INSERT INTO `diary` (id, author_id, mood_type_id, title, content, updated_at)
VALUES (1, 1, 3, '考砸了', '今天我高数只考了99分，好难过', '2021-09-30 21:00:00'),
       (2, 3, 1, '吃东西', '今天爸爸妈妈带我去吃麦当劳', '2021-11-12 19:00:00'),
       (3, 2, 7, '熬夜', '我以后再也不熬夜了', '2021-08-31 03:20:00'),
       (4, 4, 4, '金铲铲', '我差一张就追出三星剑魔了', '2022-04-21 20:00:00');

INSERT INTO `friend` (id, user_id, friend_id, state_id, validation, updated_at)
VALUES (1, 1, 2, 1, 'add me', '2021-09-30 21:00:00'),
       (2, 1, 3, 2, 'add me', '2021-09-30 21:00:00'),
       (3, 1, 4, 3, 'add me', '2021-09-30 21:00:00'),
       (4, 2, 3, 2, 'add me', '2021-09-30 21:00:00'),
       (5, 2, 1, 2, 'add me', '2021-09-30 21:00:00'),
       (6, 3, 4, 3, 'add me', '2021-09-30 21:00:00');