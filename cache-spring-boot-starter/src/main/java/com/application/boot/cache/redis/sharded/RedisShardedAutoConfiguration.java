package com.application.boot.cache.redis.sharded;

import com.application.base.cache.redis.jedis.factory.sharded.JedisShardedSessionFactory;
import com.application.base.cache.redis.jedis.factory.sharded.JedisSimpleShardedPool;
import com.application.base.cache.redis.jedis.lock.RedisDelegateDistributedLock;
import com.application.boot.cache.common.GenericPool;
import com.application.boot.cache.redis.JedisConfigProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author : 孤狼
 * @NAME: RedisSimpleAutoConfiguration
 * @DESC: 装配对象参数.
 **/
// 相当于一个普通的 java 配置类
@Configuration
// 当 JedisShardedSessionFactory 在类路径的条件下
@ConditionalOnClass({JedisShardedSessionFactory.class, RedisDelegateDistributedLock.class})
// 将 application.properties 的相关的属性字段与该类一一对应，并生成 Bean
@EnableConfigurationProperties(JedisConfigProperties.class)
public class RedisShardedAutoConfiguration {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 属性的配置.
	 */
	@Autowired
	private JedisConfigProperties jedisConfig;
	
	/**
	 *  当容器没有这个 Bean 的时候才创建这个 Bean
	 */
	@Bean
	@ConditionalOnMissingBean(JedisShardedSessionFactory.class)
	public JedisShardedSessionFactory getRedisShardedFactory() {
		return getRedisShardedInstance();
	}
	
	/**
	 *  当容器没有这个 Bean 的时候才创建这个 Bean
	 */
	@Bean
	@ConditionalOnMissingBean(RedisDelegateDistributedLock.class)
	public RedisDelegateDistributedLock getShardedDistributeLockFactory() {
		JedisShardedSessionFactory redisSimpleFactory = getRedisShardedInstance();
		RedisDelegateDistributedLock distributedLock = new RedisDelegateDistributedLock();
		distributedLock.setSessionFactory(redisSimpleFactory);
		return distributedLock;
	}
	
	/**
	 * 得到工厂实例.
	 *
	 * @return
	 */
	private JedisShardedSessionFactory getRedisShardedInstance(){
		logger.info("redis init infos:{}", jedisConfig.getSharded().toString());
		JedisPoolConfig instanceConfig = new JedisPoolConfig();
		GenericPool pool = jedisConfig.getPool();
		if (pool!=null){
			BeanUtils.copyProperties(pool,instanceConfig);
		}
		JedisConfigProperties.Sharded sharded = jedisConfig.getSharded();
		if (sharded==null){
			logger.warn("分片配置信息失败,没有找到合适的配置参数和配置项目!");
			return null;
		}
		String instances = sharded.getInstances();
		Integer sotimeout = sharded.getSotimeout();
		Integer maxattempts = sharded.getMaxattempts();
		String password = sharded.getPassword();
		Integer timeout = sharded.getTimeout();
		if (StringUtils.isBlank(instances)){
			logger.warn("Redis分片的信息获取失败!");
			return null;
		}
		JedisSimpleShardedPool shardedPool = new JedisSimpleShardedPool(instanceConfig,instances);
		if (timeout!=null && sotimeout!=null && maxattempts!=null){
			shardedPool = new JedisSimpleShardedPool(instanceConfig,timeout,sotimeout,maxattempts,instances);
		}
		if (timeout!=null && sotimeout!=null && maxattempts!=null && StringUtils.isNotBlank(password)){
			shardedPool = new JedisSimpleShardedPool(instanceConfig,timeout,sotimeout,maxattempts,password,instances);
		}
		JedisShardedSessionFactory redisShardedFactory=new JedisShardedSessionFactory();
		redisShardedFactory.setShardedPool(shardedPool);
		return redisShardedFactory;
	}
}
