-- 更新 Taku 回调验签密钥为服务端回调规则中的公共密钥
UPDATE t_ta_ku_config
SET sign = 'oypc5mtokx0vmrk7nqbctam5cz44mtn6', update_time = NOW()
WHERE `type` = 2;

-- 验证
SELECT `type`, `value`, sign, remarks FROM t_ta_ku_config WHERE `type` = 2;
