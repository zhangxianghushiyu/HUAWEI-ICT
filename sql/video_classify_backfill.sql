-- ============================================================
-- 视频分类回填脚本
-- 目的：给 t_video 里 classify_id 为 NULL 的历史视频，按 series_name
--       （系列名）规范化后回填分类；清理测试分类「哈哈」。
-- 说明：
--   1. 仅处理 del_status=1（未删除）的视频。
--   2. 新上传的视频仍由上传页指定分类，本脚本只补历史数据。
--   3. 执行前建议先备份 t_video 与 t_video_category 两张表。
--   4. 本脚本可重复执行（分类用 NOT EXISTS 幂等插入，UPDATE 按 series_name 精确匹配）。
-- ============================================================

-- ---------- 1. 新增缺失的分类（幂等） ----------
INSERT INTO t_video_category (name, sort, del_status, create_time)
SELECT t.name, 0, 1, NOW()
FROM (
    SELECT '百姓生活' AS name
    UNION SELECT '政策'
    UNION SELECT '心灵'
    UNION SELECT '旅游'
    UNION SELECT '养生'
    UNION SELECT '舞蹈'
    UNION SELECT '戏剧'
    UNION SELECT '非遗'
    UNION SELECT '老歌'
    UNION SELECT '厨艺美食'
    UNION SELECT '破洪州'
    UNION SELECT '护花使者'
) t
WHERE NOT EXISTS (SELECT 1 FROM t_video_category c WHERE c.name = t.name);

-- ---------- 2. 回填 classify_id（按 series_name 规范化映射） ----------
-- 说明：series_name 存在少量变体/脏数据，一并归并。

-- 百姓生活（含 '百姓生活18 ' 尾部空格变体）
UPDATE t_video v
JOIN t_video_category c ON c.name = '百姓生活'
SET v.classify_id = c.id
WHERE v.del_status = 1
  AND v.classify_id IS NULL
  AND (v.series_name = '百姓生活' OR TRIM(v.series_name) = '百姓生活18');

-- 政策（含 '新闻时事政策' 变体）
UPDATE t_video v
JOIN t_video_category c ON c.name = '政策'
SET v.classify_id = c.id
WHERE v.del_status = 1
  AND v.classify_id IS NULL
  AND (v.series_name = '政策' OR v.series_name = '新闻时事政策');

-- 心灵（含 '心灵鸡汤' 变体）
UPDATE t_video v
JOIN t_video_category c ON c.name = '心灵'
SET v.classify_id = c.id
WHERE v.del_status = 1
  AND v.classify_id IS NULL
  AND (v.series_name = '心灵' OR v.series_name = '心灵鸡汤');

-- 旅游
UPDATE t_video v
JOIN t_video_category c ON c.name = '旅游'
SET v.classify_id = c.id
WHERE v.del_status = 1
  AND v.classify_id IS NULL
  AND v.series_name = '旅游';

-- 养生（含 '健康养生' 变体）
UPDATE t_video v
JOIN t_video_category c ON c.name = '养生'
SET v.classify_id = c.id
WHERE v.del_status = 1
  AND v.classify_id IS NULL
  AND (v.series_name = '养生' OR v.series_name = '健康养生');

-- 舞蹈
UPDATE t_video v
JOIN t_video_category c ON c.name = '舞蹈'
SET v.classify_id = c.id
WHERE v.del_status = 1
  AND v.classify_id IS NULL
  AND v.series_name = '舞蹈';

-- 戏剧（含 '戏剧-' 变体）
UPDATE t_video v
JOIN t_video_category c ON c.name = '戏剧'
SET v.classify_id = c.id
WHERE v.del_status = 1
  AND v.classify_id IS NULL
  AND (v.series_name = '戏剧' OR v.series_name = '戏剧-');

-- 非遗（含 '皮影戏'：皮影戏归入非遗）
UPDATE t_video v
JOIN t_video_category c ON c.name = '非遗'
SET v.classify_id = c.id
WHERE v.del_status = 1
  AND v.classify_id IS NULL
  AND (v.series_name = '非遗' OR v.series_name = '皮影戏');

-- 老歌
UPDATE t_video v
JOIN t_video_category c ON c.name = '老歌'
SET v.classify_id = c.id
WHERE v.del_status = 1
  AND v.classify_id IS NULL
  AND v.series_name = '老歌';

-- 厨艺美食
UPDATE t_video v
JOIN t_video_category c ON c.name = '厨艺美食'
SET v.classify_id = c.id
WHERE v.del_status = 1
  AND v.classify_id IS NULL
  AND v.series_name = '厨艺美食';

-- 破洪州（含 '破洪州 part' 变体）
UPDATE t_video v
JOIN t_video_category c ON c.name = '破洪州'
SET v.classify_id = c.id
WHERE v.del_status = 1
  AND v.classify_id IS NULL
  AND (v.series_name = '破洪州' OR v.series_name = '破洪州 part');

-- 护花使者（含 '护花使者 part' / '护花使者part' 变体）
UPDATE t_video v
JOIN t_video_category c ON c.name = '护花使者'
SET v.classify_id = c.id
WHERE v.del_status = 1
  AND v.classify_id IS NULL
  AND (v.series_name = '护花使者'
       OR v.series_name = '护花使者 part'
       OR v.series_name = '护花使者part');

-- ---------- 3. 处理「哈哈」分类下的 2 条养生视频 ----------
-- 这 2 条 series_name = '11'，内容是养生，归入「养生」分类。
UPDATE t_video v
JOIN t_video_category c ON c.name = '养生'
SET v.classify_id = c.id
WHERE v.del_status = 1
  AND v.series_name = '11';

-- ---------- 4. 逻辑删除「哈哈」测试分类 ----------
UPDATE t_video_category SET del_status = 0 WHERE name = '哈哈';

-- ---------- 5. 校验（可选，执行后取消注释查看） ----------
-- SELECT classify_id, count(*) FROM t_video WHERE del_status = 1 GROUP BY classify_id ORDER BY classify_id;
-- SELECT id, name, del_status FROM t_video_category ORDER BY id;
