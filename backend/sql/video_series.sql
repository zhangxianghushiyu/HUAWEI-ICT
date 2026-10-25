-- ============================================================
-- 娱乐模块：视频系列化改造 DDL
-- 说明：
--   1. 给 t_video 增加 series_name（系列名）与 episode_num（集数）两列。
--   2. 新增 t_video_publicize 视频系列解锁记录表。
--   历史数据的 series_name / episode_num 回填：二选一
--   （方式一）MySQL 8.0+ 直接执行本文件末尾的 UPDATE；
--   （方式二）MySQL 5.7 或无正则函数时，执行完 DDL 后调后台接口 POST /platform/video/refreshVideoSeries。
-- ============================================================

ALTER TABLE t_video
    ADD COLUMN series_name varchar(255) DEFAULT NULL COMMENT '系列名（名称去掉末尾数字）' AFTER `name`,
    ADD COLUMN episode_num int DEFAULT NULL COMMENT '集数（名称末尾数字）' AFTER series_name;

CREATE TABLE IF NOT EXISTS t_video_publicize (
    id int NOT NULL AUTO_INCREMENT COMMENT '主键',
    user_id int DEFAULT NULL COMMENT '用户ID',
    video_id int DEFAULT NULL COMMENT '视频ID',
    `unlock` int DEFAULT NULL COMMENT '0未解锁 1已解锁',
    create_time datetime DEFAULT NULL COMMENT '创建时间',
    update_time datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_user_video (user_id, video_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='视频系列解锁记录';

-- ============================================================
-- 历史数据回填（仅 MySQL 8.0+；5.7 请改用后台接口）
-- 按名称末尾数字：series_name 去掉末尾数字，episode_num 取末尾数字
-- ============================================================
UPDATE t_video
SET series_name = REGEXP_REPLACE(`name`, '[0-9]+$', ''),
    episode_num = CAST(REGEXP_SUBSTR(`name`, '[0-9]+$') AS UNSIGNED)
WHERE `name` IS NOT NULL AND `name` != '';

-- ============================================================
-- 视频分类（合集模块）
-- ============================================================
ALTER TABLE t_video
    ADD COLUMN classify_id int DEFAULT NULL COMMENT '视频分类ID' AFTER drama_id;

CREATE TABLE IF NOT EXISTS t_video_category (
    id int NOT NULL AUTO_INCREMENT COMMENT '主键',
    sort int DEFAULT 0 COMMENT '排序',
    name varchar(64) DEFAULT NULL COMMENT '名称',
    del_status int DEFAULT 1 COMMENT '删除状态 1正常 0删除',
    create_time datetime DEFAULT NULL,
    update_time datetime DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='视频分类';
