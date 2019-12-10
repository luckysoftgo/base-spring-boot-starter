package com.application.boot.cache.redis.cluster;

import com.application.base.cache.redis.jedis.factory.cluster.JedisClusterSessionFactory;
import com.application.base.cache.redis.jedis.factory.cluster.RedisClusterPool;
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
// 当 JedisClusterSessionFactory 在类路径的条件下
@ConditionalOnClass({JedisClusterSessionFactory.class, RedisDelegateDistributedLock.class})
// 将 application.properties 的相关的属性字段与该类一一对应，并生成 Bean
@EnableConfigurationProperties(JedisConfigProperties.class)
public class RedisClusterAutoConfiguration {

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
	@ConditionalOnMissingBean(JedisClusterSessionFactory.class)
	public JedisClusterSessionFactory getRedisClusterFactory() {
		return getRedisClusterInstance();
	}
	
	/**
	 *  当容器没有这个 Bean 的时候才创建这个 Bean
	 */
	@Bean
	@ConditionalOnMissingBean(RedisDelegateDistributedLock.class)
	public RedisDelegateDistributedLock getClusterDistributeLockFactory() {
		JedisClusterSessionFactory redisSimpleFactory = getRedisClusterInstance();
		RedisDelegateDistributedLock distributedLock = new RedisDelegateDistributedLock();
		distributedLock.setSessionFactory(redisSimpleFactory);
		return distributedLock;
	}
	
	/**
	 * 得到工厂实例.
	 *
	 * @return
	 */
	private JedisClusterSessionFactory getRedisClusterInstance(){
		logger.info("redis init infos:{}", jedisConfig.getCluster().toString());
		JedisPoolConfig instanceConfig = new JedisPoolConfig();
		GenericPool pool = jedisConfig.getPool();
		if (pool!=null){
			BeanUtils.copyProperties(pool,instanceConfig);
		}
		JedisConfigProperties.Cluster cluster = jedisConfig.getCluster();
		if (cluster==null){
			logger.warn("集群配置信息失败,没有找到合适的配置参数和配置项目!");
			return null;
		}
		String instances = cluster.getInstances();
		Integer sotimeout = cluster.getSotimeout();
		Integer maxattempts = cluster.getMaxattempts();
		String password = cluster.getPassword();
		Integer timeout = cluster.getTimeout();
		if (StringUtils.isBlank(instances)){
			logger.warn("集群主机连接信息为空!");
			return null;
		}
		RedisClusterPool clusterPool = new RedisClusterPool(instanceConfig,instances);
		if (timeout!=null && sotimeout!=null && maxattempts!=null){
			clusterPool = new RedisClusterPool(instanceConfig,timeout,sotimeout,maxattempts,instances);
		}
		if (timeout!=null && sotimeout!=null && maxattempts!=null && StringUtils.isNotBlank(password)){
			clusterPool = new RedisClusterPool(instanceConfig,timeout,sotimeout,maxattempts,password,instances);
		}
		JedisClusterSessionFactory redisClusterFactory = new JedisClusterSessionFactory();
		redisClusterFactory.setClusterPool(clusterPool);
		return redisClusterFactory;
	}
}
