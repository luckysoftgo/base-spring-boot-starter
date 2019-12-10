package com.application.boot.cache.redis.complex;

import com.application.base.cache.redis.jedis.factory.complex.ShardedJedisSentinelFactory;
import com.application.base.cache.redis.jedis.factory.complex.ShardedJedisSentinelPool;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author : 孤狼
 * @NAME: RedisSimpleAutoConfiguration
 * @DESC: 装配对象参数.
 **/
// 相当于一个普通的 java 配置类
@Configuration
// 当 ShardedJedisSentinelFactory 在类路径的条件下
@ConditionalOnClass({ShardedJedisSentinelFactory.class, RedisDelegateDistributedLock.class})
// 将 application.properties 的相关的属性字段与该类一一对应，并生成 Bean
@EnableConfigurationProperties(JedisConfigProperties.class)
public class RedisComplexAutoConfiguration {

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
	@ConditionalOnMissingBean(ShardedJedisSentinelFactory.class)
	public ShardedJedisSentinelFactory getRedisComplexFactory() {
		return getShardedSentinelInstance();
	}
	
	/**
	 *  当容器没有这个 Bean 的时候才创建这个 Bean
	 */
	@Bean
	@ConditionalOnMissingBean(RedisDelegateDistributedLock.class)
	public RedisDelegateDistributedLock getComplexDistributeLockFactory() {
		ShardedJedisSentinelFactory redisSimpleFactory = getShardedSentinelInstance();
		RedisDelegateDistributedLock distributedLock = new RedisDelegateDistributedLock();
		distributedLock.setSessionFactory(redisSimpleFactory);
		return distributedLock;
	}
	
	/**
	 * 得到工厂实例.
	 *
	 * @return
	 */
	private ShardedJedisSentinelFactory getShardedSentinelInstance(){
		logger.info("redis init infos:{}", jedisConfig.getComplex().toString());
		JedisPoolConfig instanceConfig = new JedisPoolConfig();
		GenericPool pool = jedisConfig.getPool();
		if (pool!=null){
			BeanUtils.copyProperties(pool,instanceConfig);
		}
		JedisConfigProperties.Complex complex = jedisConfig.getComplex();
		if (complex==null){
			logger.warn("哨兵分片配置信息失败,没有找到合适的配置参数和配置项目!");
			return null;
		}
		String password = complex.getPassword();
		List<JedisConfigProperties.IpPortInfo> masters  = complex.getMasters();
		List<JedisConfigProperties.IpPortInfo> sentinels = complex.getSentinels();
		Integer timeout = complex.getTimeout();
		if (masters.isEmpty() || sentinels.isEmpty()){
			logger.warn("哨兵,分片的方式参数传入为空!");
			return null;
		}
		List<String> masterConnects = new ArrayList<>();
		for (JedisConfigProperties.IpPortInfo info : masters) {
			masterConnects.add(info.getInfo());
		}
		Set<String> sentinelConnects = new HashSet<>();
		for (JedisConfigProperties.IpPortInfo info : sentinels) {
			sentinelConnects.add(info.getInfo());
		}
		ShardedJedisSentinelPool shardedSentinelPool = new ShardedJedisSentinelPool(instanceConfig,masterConnects,sentinelConnects);
		if (StringUtils.isNotBlank(password) && timeout==null){
			shardedSentinelPool = new ShardedJedisSentinelPool(masterConnects,sentinelConnects,instanceConfig,password);
		}
		if (StringUtils.isBlank(password) && timeout!=null){
			shardedSentinelPool = new ShardedJedisSentinelPool(masterConnects,sentinelConnects,instanceConfig,timeout);
		}
		if (StringUtils.isNotBlank(password) && timeout!=null){
			shardedSentinelPool = new ShardedJedisSentinelPool(masterConnects,sentinelConnects,instanceConfig,timeout,password);
		}
		ShardedJedisSentinelFactory redisSentinelFactory = new ShardedJedisSentinelFactory();
		redisSentinelFactory.setComplexPool(shardedSentinelPool);
		return redisSentinelFactory;
	}
}
