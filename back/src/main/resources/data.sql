INSERT INTO `mood` (id, name)
VALUES (1, '开心'),
       (2, '期待'),
       (3, '难过'),
       (4, '平静'),
       (5, '幸福'),
       (6, '后悔');

INSERT INTO `request_state` (id, name)
VALUES (1, '待审核'),
       (2, '已通过'),
       (3, '已拒绝');

INSERT INTO `user` (id, username, password)
VALUES (1, 'Mike', 'password'),
       (2, 'Jane', 'password'),
       (3, 'Jack', 'password'),
       (4, 'Bill', 'password'),
       (5, 'Emily', 'password'),
       (6, 'Alex', 'password'),
       (7, 'Sarah', 'password'),
       (8, 'Chris', 'password'),
       (9, 'Olivia', 'password'),
       (10, 'Daniel', 'password'),
       (11, 'Sophia', 'password'),
       (12, 'David', 'password'),
       (13, 'Emma', 'password'),
       (14, 'James', 'password'),
       (15, 'Lily', 'password'),
       (16, 'Jacob', 'password'),
       (17, 'Ava', 'password'),
       (18, 'Matthew', 'password'),
       (19, 'Grace', 'password'),
       (20, 'Samuel', 'password');

INSERT INTO `diary` (id, author_id, mood_type_id, title, content, updated_at)
VALUES (1, 1, 3, '考砸了', '今天我高数只考了99分，好难过', '2023-12-01 19:00:00'),
       (2, 2, 1, '吃东西', '今天爸爸妈妈带我去吃麦当劳', '2023-12-02 19:00:00'),
       (3, 3, 6, '熬夜', '我以后再也不熬夜了', '2023-12-03 19:00:00'),
       (4, 4, 3, '金铲铲', '我差一张就追出三星剑魔了', '2023-12-04 19:00:00'),
       (5, 1, 5, '旅行回忆', '我在海滩度过了美好的一天', '2023-12-05 19:00:00'),
       (6, 2, 1, '音乐会', '今晚的音乐会表演太精彩了', '2023-12-06 19:00:00'),
       (7, 3, 4, '新书推荐', '我刚读完一本非常好的小说，强烈推荐给大家', '2023-12-17 19:00:00'),
       (8, 4, 1, '登山探险', '我成功登上了一座高峰，风景太壮观了', '2023-11-01 19:00:00'),
       (9, 1, 5, '友谊', '今天和朋友们一起度过了愉快的时光', '2023-11-02 19:00:00'),
       (10, 2, 1, '学业进展', '我在考试中取得了很好的成绩，感到非常自豪', '2023-11-03 19:00:00'),
       (11, 3, 1, '喜剧电影', '看了一部非常搞笑的喜剧电影，笑得肚子都疼了', '2023-11-04 19:00:00'),
       (12, 4, 1, '家庭聚会', '今天全家人一起吃了一顿丰盛的晚餐', '2023-11-05 19:00:00'),
       (13, 1, 5, '绘画艺术', '我画了一幅美丽的风景画，展示给朋友们看', '2023-11-06 19:00:00'),
       (14, 4, 1, '新技能学习', '我学会了一项有用的技能，感觉自己进步了', '2023-11-07 19:00:00'),
       (15, 2, 1, '庆生派对', '朋友们给我举办了一个惊喜的生日派对', '2023-11-09 19:00:00'),
       (16, 3, 1, '户外运动', '我和朋友们一起打篮球，玩得很开心', '2023-11-10 19:00:00'),
       (17, 2, 1, '新宠物', '我领养了一只可爱的小猫，它非常调皮', '2023-11-11 19:00:00'),
       (18, 1, 1, '演讲比赛', '我在学校的演讲比赛中获得了第一名', '2023-11-12 19:00:00'),
       (19, 2, 2, '创业计划', '我有了一项新的商业创意，希望能实现', '2023-11-17 19:00:00'),
       (20, 3, 2, '健身成果', '经过几个月的锻炼，我的身体变得更加健康', '2023-11-17 19:00:00'),
       (21, 4, 3, '失去了重要的人', '今天我失去了一个非常重要的朋友，心情非常悲伤', '2023-12-11 19:00:00'),
       (22, 1, 6, '后悔的决定', '我后悔之前做出的某个决定，希望能够重新来过', '2023-12-12 19:00:00'),
       (23, 2, 4, '宁静的夜晚', '我在安静的夜晚散步，心情变得平静而宁静', '2023-12-13 19:00:00'),
       (24, 3, 6, '失去机会', '我错过了一个非常好的机会，感到非常遗憾和悲伤', '2023-12-14 19:00:00'),
       (25, 4, 4, '寻找内心的平静', '我正在尝试寻找内心的平静和宁静，希望能够找到答案', '2023-12-15 19:00:00'),
       (26, 1, 4, '回忆过去', '回忆起过去的一段时光，心情变得有些悲伤和伤感', '2023-12-16 19:00:00');


INSERT INTO `friend` (id, user_id, friend_id, state_id, validation, updated_at)
VALUES (1, 1, 2, 2, '你喜欢读书吗？我们可以一起交流心得', '2021-09-30 21:00:00'),
       (2, 1, 3, 2, '我们在同一个社团，可以认识一下吗？', '2021-09-30 21:00:00'),
        (3, 4, 2, 2, '我们有很多共同的兴趣爱好，希望能成为朋友', '2023-12-17 21:00:00'),
        (4, 5, 1, 1, '我在学校见过你，可以加你好友吗？', '2023-12-17 21:00:00'),
        (5, 6, 2, 1, '你对游戏感兴趣吗？我们可以一起玩', '2023-12-17 21:00:00'),
        (6, 7, 1, 2, '听说你擅长音乐，可以向你请教吗？', '2023-12-17 21:00:00'),
        (7, 8, 2, 1, '我喜欢你的绘画作品，能不能加你好友？', '2023-12-17 21:00:00'),
        (8, 9, 1, 1, '我们住在同一个小区，可以认识一下吗？', '2023-12-17 21:00:00'),
        (9, 10, 2, 1, '我看到你的演讲很棒，能不能加你好友？', '2023-12-17 21:00:00'),
        (10, 11, 1, 1, '你对摄影有兴趣吗？我们可以一起拍照', '2023-12-17 21:00:00'),
        (11, 12, 2, 1, '我在社交网络上看到你的文章，很喜欢', '2023-12-17 21:00:00'),
        (12, 13, 1, 1, '我们在同一个俱乐部，可以认识一下吗？', '2023-12-17 21:00:00'),
        (13, 14, 2, 2, '我们喜欢同一部电影，可以一起讨论吗？', '2023-12-17 21:00:00'),
        (14, 15, 1, 2, '我听说你喜欢旅行，我们可以一起规划行程', '2023-12-17 21:00:00'),
        (15, 16, 2, 2, '你对科技有研究吗？我们可以交流一下', '2023-12-17 21:00:00'),
        (16, 17, 1, 2, '我喜欢你的写作风格，能不能加你好友？', '2023-12-17 21:00:00'),
        (17, 18, 2, 2, '你对健身有兴趣吗？我们可以一起锻炼', '2023-12-17 21:00:00');


INSERT INTO `mood_score` (id, user_id, mood_score, date)
VALUES (1, 1, 100, '2023-11-01'),
       (2, 1, 90, '2023-11-02'),
       (3, 1, 80, '2023-11-03'),
       (4, 1, 70, '2023-11-04'),
       (5, 1, 60, '2023-11-05'),
       (6, 1, 70, '2023-11-06'),
       (7, 1, 88, '2023-11-07'),
       (8, 1, 50, '2023-11-08'),
       (9, 1, 60, '2023-11-09'),
       (10, 1, 70, '2023-11-10'),
       (11, 1, 80, '2023-11-11'),
       (12, 1, 90, '2023-11-12'),
       (13, 1, 100, '2023-11-13'),
       (14, 1, 90, '2023-11-14'),
       (15, 1, 80, '2023-11-15'),
       (16, 1, 70, '2023-11-16'),
       (17, 1, 60, '2023-11-17'),
       (18, 1, 70, '2023-11-18'),
       (19, 1, 88, '2023-11-19'),
       (20, 1, 95, '2023-11-20'),
       (21, 1, 85, '2023-11-21'),
       (22, 1, 75, '2023-11-22'),
       (23, 1, 65, '2023-11-23'),
       (24, 1, 75, '2023-11-24'),
       (25, 1, 83, '2023-11-25'),
       (26, 1, 92, '2023-11-26'),
       (27, 1, 97, '2023-11-27'),
       (28, 1, 88, '2023-11-28'),
       (29, 1, 78, '2023-11-29'),
       (30, 1, 69, '2023-11-30'),
       (31, 1, 72, '2023-12-01'),
       (32, 1, 81, '2023-12-02'),
       (33, 1, 90, '2023-12-03'),
       (34, 1, 95, '2023-12-04'),
       (35, 1, 86, '2023-12-05'),
       (36, 1, 77, '2023-12-06'),
       (37, 1, 68, '2023-12-07'),
       (38, 1, 79, '2023-12-08'),
       (39, 1, 88, '2023-12-09'),
       (40, 1, 96, '2023-12-10'),
       (41, 1, 91, '2023-12-11'),
       (42, 1, 85, '2023-12-12'),
       (43, 1, 79, '2023-12-13'),
       (44, 1, 73, '2023-12-14'),
       (45, 1, 67, '2023-12-15');

