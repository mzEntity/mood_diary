DROP TABLE IF EXISTS `mood_score`;
DROP TABLE IF EXISTS `diary`;
DROP TABLE IF EXISTS `friend`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `request_state`;
DROP TABLE IF EXISTS `mood`;

CREATE TABLE `mood`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `name`     varchar(15) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='心情种类表';

CREATE TABLE `request_state`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `name`     varchar(15) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='申请状态表';

CREATE TABLE `user`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `username`    varchar(50)  NOT NULL COMMENT '用户名',
    `password`    varchar(255) NOT NULL COMMENT '密码',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';


CREATE TABLE `diary`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `author_id`     int(11) NOT NULL,
    `mood_type_id`  int(3) NOT NULL,
    `title`    varchar(255) NOT NULL COMMENT '标题',
    `content`    TEXT NOT NULL COMMENT '内容',
    `avatar`    TEXT,
    `updated_at` timestamp COMMENT '更新日期',

    PRIMARY KEY (`id`),
    FOREIGN KEY (`author_id`) REFERENCES user(`id`),
    FOREIGN KEY (`mood_type_id`) REFERENCES mood(`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='日记表';


CREATE TABLE `friend`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT,
    `user_id`          int(11) NOT NULL,
    `friend_id`     int(11) NOT NULL,
    `state_id`      int(11) NOT NULL,
    `validation`    TEXT NOT NULL COMMENT '验证消息',
    `updated_at` timestamp COMMENT '更新日期',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES user(`id`),
    FOREIGN KEY (`friend_id`) REFERENCES user(`id`),
    FOREIGN KEY (`state_id`) REFERENCES request_state(`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='好友表';

CREATE TABLE `mood_score`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `user_id`     int(11) NOT NULL,
    `mood_score`  int(3) NOT NULL,
    `date` DATE COMMENT '提交日期',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES user(`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='心情打分表';