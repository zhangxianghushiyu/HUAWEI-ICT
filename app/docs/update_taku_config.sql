-- ============================================================
-- Taku 广告配置切换脚本
-- 从旧应用 a692faba10d15a 切换到新应用 a6a2768f6227eb
-- 目标表：t_ta_ku_config
-- 执行前请确认 sign（TYPE_2 的回调密钥）已在 Taku 后台更新
-- ============================================================

-- 1. 开屏广告
UPDATE t_ta_ku_config
SET `value` = 'b6a2769587c201', update_time = NOW()
WHERE `type` = 1;

-- 2. 激励视频广告位（sign 字段为 Taku 服务器回调验签密钥，需与 Taku 后台一致）
UPDATE t_ta_ku_config
SET `value` = 'b6a27695a5cd47', update_time = NOW()
WHERE `type` = 2;

-- 3. Taku App ID
UPDATE t_ta_ku_config
SET `value` = 'a6a2768f6227eb', update_time = NOW()
WHERE `type` = 3;

-- 4. Taku App Key
UPDATE t_ta_ku_config
SET `value` = 'a6af687f456b60b76907f9a9b8cbbc73f', update_time = NOW()
WHERE `type` = 4;

-- 5. 顶部 Banner
UPDATE t_ta_ku_config
SET `value` = 'b6a27695b77b77', update_time = NOW()
WHERE `type` = 5;

-- 6. 底部 Banner
UPDATE t_ta_ku_config
SET `value` = 'b6a27695b77b77', update_time = NOW()
WHERE `type` = 6;

-- 7. 插屏广告
UPDATE t_ta_ku_config
SET `value` = 'b6a27695ae4bad', update_time = NOW()
WHERE `type` = 7;

-- 8. 原生广告
UPDATE t_ta_ku_config
SET `value` = 'b6a276959b29c7', update_time = NOW()
WHERE `type` = 8;

-- ============================================================
-- 验证 SQL
-- ============================================================
SELECT `type`, `value`, sign, remarks FROM t_ta_ku_config ORDER BY `type`;
