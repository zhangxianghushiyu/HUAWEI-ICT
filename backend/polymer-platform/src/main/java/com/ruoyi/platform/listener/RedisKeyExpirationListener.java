
package com.ruoyi.platform.listener;

import cn.hutool.core.util.StrUtil;
import com.ruoyi.platform.config.RedisConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

/**
 * redis过期监听
 */
@Component
@Slf4j
public class RedisKeyExpirationListener implements MessageListener {

    //将redis的key过期推送功能打开

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedisConfigProperties redisConfigProperties;

    public RedisKeyExpirationListener(RedisTemplate<String, String> redisTemplate,
                                      RedisConfigProperties redisConfigProperties) {
        this.redisTemplate = redisTemplate;
        this.redisConfigProperties = redisConfigProperties;
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        RedisSerializer<?> serializer = redisTemplate.getValueSerializer();
        String channel = String.valueOf(serializer.deserialize(message.getChannel()));
        String body = String.valueOf(serializer.deserialize(message.getBody()));
        //key过期监听
        if (StrUtil.format("__keyevent@{}__:expired", redisConfigProperties.getDatabase()).equals(channel)) {
            log.info("redis过期的key:{}", body);
            //取redis的值进行处理
//            body = body.replace(Constant.REDIS_LISTENER_TEST, "");
//            String[] str = body.split(":");
//            Integer id = Integer.parseInt(str[0]);
//            User user = userDao.selectByPrimaryKey(id);
//            log.info(user.toString());
        }
    }
}
