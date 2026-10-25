-- 查看金币兑换比例配置
SELECT * FROM t_convert_ratio;

-- 查看用户 28 当前金币和现金
SELECT id, nick_name, gold_coin, cash FROM t_user WHERE id = 28;

-- 查看金币账单明细
SELECT * FROM t_bill_gold WHERE user_id = 28 ORDER BY create_time DESC LIMIT 5;
