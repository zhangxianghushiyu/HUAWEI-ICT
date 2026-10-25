
package com.ruoyi.platform.utils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


/**
 * 在线用户统计工具类
 * 参考：https://www.jb51.net/article/210809.htm
 */
@Component
public class UserOnlineUtil {
	private static final String USERS_ONLINE_INFO = "users:online:info";

	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 添加用户在线信息
	 * @param userId
	 * @return
	 */
	public Boolean online(Integer userId) {
		return this.redisTemplate.opsForZSet().add(USERS_ONLINE_INFO, userId, Instant.now().toEpochMilli());
	}

	/**
	 * 获取一定时间内，在线的用户数量
	 * @param duration
	 * @return
	 */
	public Long count(Duration duration) {
		LocalDateTime now = LocalDateTime.now();
		return this.redisTemplate.opsForZSet().count(USERS_ONLINE_INFO,
				now.minus(duration).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
				now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
	}


	/**
	 * 获取所有在线过的用户数量，不论时间
	 * @return
	 */
	public Long count() {
		return this.redisTemplate.opsForZSet().zCard(USERS_ONLINE_INFO);
	}

	/**
	 * 清除超过一定时间没在线的用户数据
	 * @param duration
	 * @return
	 */
	public Long clear(Duration duration) {
		return this.redisTemplate.opsForZSet().removeRangeByScore(USERS_ONLINE_INFO, 0,
				LocalDateTime.now().minus(duration).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
	}


}
