package com.application.boot.cache.redis.sentinel;

import com.application.base.cache.redis.jedis.factory.sentinel.JedisSentinelSessionFactory;
import com.application.base.cache.redis.jedis.factory.sentinel.JedisSimpleSentinelPool;
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
// 当 JedisSentinelSessionFactory 在类路径的条件下
@ConditionalOnClass({JedisSentinelSessionFactory.class, RedisDelegateDistributedLock.class})
// 将 application.properties 的相关的属性字段与该类一一对应，并生成 Bean
@EnableConfigurationProperties(JedisConfigProperties.class)
public class RedisSentinelAutoConfiguration {

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
	@ConditionalOnMissingBean(JedisSentinelSessionFactory.class)
	public JedisSentinelSessionFactory getRedisSentinelFactory() {
		return getRedisSentinelInstance();
	}
	
	/**
	 *  当容器没有这个 Bean 的时候才创建这个 Bean
	 */
	@Bean
	@ConditionalOnMissingBean(RedisDelegateDistributedLock.class)
	public RedisDelegateDistributedLock getSentinelDistributeLockFactory() {
		JedisSentinelSessionFactory redisSimpleFactory = getRedisSentinelInstance();
		RedisDelegateDistributedLock distributedLock = new RedisDelegateDistributedLock();
		distributedLock.setSessionFactory(redisSimpleFactory);
		return distributedLock;
	}
	
	/**
	 * 得到工厂实例.
	 *
	 * @return
	 */
	private JedisSentinelSessionFactory getRedisSentinelInstance(){
		logger.info("redis init infos:{}", jedisConfig.getSentinel().toString());
		JedisPoolConfig instanceConfig = new JedisPoolConfig();
		GenericPool pool = jedisConfig.getPool();
		if (pool!=null){
			BeanUtils.copyProperties(pool,instanceConfig);
		}
		JedisConfigProperties.Sentinel sentinel = jedisConfig.getSentinel();
		if (sentinel==null){
			logger.warn("哨兵配置信息失败,没有找到合适的配置参数和配置项目!");
			return null;
		}
		List<JedisConfigProperties.IpPortInfo> sentinels = sentinel.getSentinels();
		String mastername = sentinel.getMastername();
		String instances = sentinel.getInstances();
		Integer timeout = sentinel.getTimeout();
		if (StringUtils.isBlank(mastername)){
			logger.warn("哨兵模式下获得master节点信息失败!");
			return null;
		}
		JedisSimpleSentinelPool sentinelPool = new JedisSimpleSentinelPool();
		sentinelPool.setPoolConfig(instanceConfig);
		if (StringUtils.isNotBlank(mastername) && StringUtils.isNotBlank(instances)){
			sentinelPool.setHostInfos(instances);
			sentinelPool.setMasterName(mastername);
		}
		if (!sentinels.isEmpty()){
			Set<String> connects = new HashSet<>();
			for (JedisConfigProperties.IpPortInfo info : sentinels ) {
				connects.add(info.getInfo());
			}
			sentinelPool.setSentinels(connects);
		}
		if (timeout!=null){
			sentinelPool.setTimeout(timeout);
		}
		JedisSentinelSessionFactory redisSentinelFactory=new JedisSentinelSessionFactory();
		redisSentinelFactory.setSentinelPool(sentinelPool);
		return redisSentinelFactory;
	}
}
