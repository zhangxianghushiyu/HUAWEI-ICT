
package com.ruoyi.platform.config;

import cn.hutool.core.util.StrUtil;
import com.ruoyi.platform.listener.RedisKeyExpirationListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * redis监听器配置
 *
 */
@Configuration
public class RedisListenerConfig {

	//将redis的key过期推送功能打开

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private RedisConfigProperties redisConfigProperties;

	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);

		container.addMessageListener(new RedisKeyExpirationListener(redisTemplate,redisConfigProperties
				), new PatternTopic(StrUtil.format("__keyevent@{}__:expired", redisConfigProperties.getDatabase())));
		return container;
	}
}

