
package com.ruoyi.platform.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * reids相关配置
 *
 * @author
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfigProperties {

	private String host;
	private String port;
	private String password;
	private String database;

}
