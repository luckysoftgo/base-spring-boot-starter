package com.application.boot.cache.redis.simple;

import com.application.base.cache.redis.jedis.factory.simple.JedisSimpleSessionFactory;
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
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author : 孤狼
 * @NAME: RedisSimpleAutoConfiguration
 * @DESC: 装配对象参数.
 **/
// 相当于一个普通的 java 配置类
@Configuration
// 当 JedisSimpleSessionFactory 在类路径的条件下
@ConditionalOnClass({JedisSimpleSessionFactory.class, RedisDelegateDistributedLock.class})
// 将 application.properties 的相关的属性字段与该类一一对应，并生成 Bean
@EnableConfigurationProperties(JedisConfigProperties.class)
public class RedisSimpleAutoConfiguration {

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
	@ConditionalOnMissingBean(JedisSimpleSessionFactory.class)
	public JedisSimpleSessionFactory getRedisSimpleFactory() {
		return getRedisSimpleInstance();
	}
	
	/**
	 *  当容器没有这个 Bean 的时候才创建这个 Bean
	 */
	@Bean
	@ConditionalOnMissingBean(RedisDelegateDistributedLock.class)
	public RedisDelegateDistributedLock getSimpleDistributeLockFactory() {
		JedisSimpleSessionFactory redisSimpleFactory = getRedisSimpleInstance();
		RedisDelegateDistributedLock distributedLock = new RedisDelegateDistributedLock();
		distributedLock.setSessionFactory(redisSimpleFactory);
		return distributedLock;
	}
	
	/**
	 * 得到工厂实例.
	 *
	 * @return
	 */
	private JedisSimpleSessionFactory getRedisSimpleInstance(){
		logger.info("redis init infos:{}", jedisConfig.getSimple().toString());
		JedisPoolConfig instanceConfig = new JedisPoolConfig();
		GenericPool pool = jedisConfig.getPool();
		if (pool!=null){
			BeanUtils.copyProperties(pool,instanceConfig);
		}
		JedisConfigProperties.Simple simple = jedisConfig.getSimple();
		if (simple==null){
			logger.warn("单机配置信息失败,没有找到合适的配置参数和配置项目!");
			return null;
		}
		String host = simple.getHost();
		Integer port = simple.getPort();
		Integer database = simple.getDatabase();
		Integer timeout = simple.getTimeout();
		String password = simple.getPassword();
		if (StringUtils.isBlank(host)){
			logger.warn("单机情况下的主机信息获取失败!");
			return null;
		}
		JedisPool jedisPool = new JedisPool(instanceConfig,host,port);
		if (timeout!=null){
			jedisPool = new JedisPool(instanceConfig,host,port,timeout);
		}
		if ( timeout!=null && StringUtils.isNotBlank(password)){
			jedisPool = new JedisPool(instanceConfig,host,port,timeout,password);
		}
		if (database!=null && timeout!=null && StringUtils.isNotBlank(password)){
			jedisPool = new JedisPool(instanceConfig,host,port,timeout,password,database);
		}
		JedisSimpleSessionFactory redisSimpleFactory=new JedisSimpleSessionFactory();
		redisSimpleFactory.setJedisPool(jedisPool);
		return redisSimpleFactory;
	}
}
