package com.application.boot.cache.redisson;

import com.application.base.cache.redisson.redisson.factory.RedissonInstanceSessionFactory;
import com.application.base.cache.redisson.redisson.lock.RedissonDelegateDistributedLock;
import com.application.base.cache.redisson.redisson.pool.RedissonInstancePool;
import com.application.base.cache.redisson.redisson.pool.config.RedissonBasicConfig;
import com.application.base.cache.redisson.redisson.pool.config.RedissonCloudConfig;
import com.application.base.cache.redisson.redisson.pool.config.RedissonClusterConfig;
import com.application.base.cache.redisson.redisson.pool.config.RedissonMasterSlaveConfig;
import com.application.base.cache.redisson.redisson.pool.config.RedissonSentinelConfig;
import com.application.base.cache.redisson.redisson.pool.config.RedissonSimpleConfig;
import com.application.boot.cache.common.GenericPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : 孤狼
 * @NAME: RedisSimpleAutoConfiguration
 * @DESC: 装配对象参数.
 **/
// 相当于一个普通的 java 配置类
@Configuration
// 当 RedissonInstanceSessionFactory 在类路径的条件下
@ConditionalOnClass({RedissonInstanceSessionFactory.class, RedissonDelegateDistributedLock.class})
// 将 application.properties 的相关的属性字段与该类一一对应，并生成 Bean
@EnableConfigurationProperties(RedissonConfigProperties.class)
public class RedissonAutoConfiguration {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 属性的配置.
	 */
	@Autowired
	private RedissonConfigProperties redissonConfig;
	
	/**
	 *  当容器没有这个 Bean 的时候才创建这个 Bean
	 */
	@Bean
	@ConditionalOnMissingBean(RedissonInstanceSessionFactory.class)
	public RedissonInstanceSessionFactory getRedissonInstancFactory() {
		return getRedissonInstance();
	}

	
	/**
	 *  当容器没有这个 Bean 的时候才创建这个 Bean
	 */
	@Bean
	@ConditionalOnMissingBean(RedissonDelegateDistributedLock.class)
	public RedissonDelegateDistributedLock getRedissonDistributeLockFactory() {
		RedissonInstanceSessionFactory redisSimpleFactory = getRedissonInstance();
		RedissonDelegateDistributedLock distributedLock = new RedissonDelegateDistributedLock();
		distributedLock.setCloudLockFactory(redisSimpleFactory);
		return distributedLock;
	}
	
	/**
	 * 获得实例操作.
	 * @return
	 */
	private RedissonInstanceSessionFactory getRedissonInstance() {
		GenericObjectPoolConfig genericPoolConfig = new GenericObjectPoolConfig();
		GenericPool pool = redissonConfig.getPool();
		if (pool!=null){
			BeanUtils.copyProperties(pool,genericPoolConfig);
		}
		String model = redissonConfig.getModel();
		RedissonBasicConfig basicConfig = null;
		if (model.equalsIgnoreCase(ModelType.SIMPLE.getModel())){
			basicConfig = getSimpleConfig();
		}else if (model.equalsIgnoreCase(ModelType.SENTINEL.getModel())){
			basicConfig = getSentinelConfig();
		}else if (model.equalsIgnoreCase(ModelType.MASTERSLAVE.getModel())){
			basicConfig = getMasterSlaveConfig();
		}else if (model.equalsIgnoreCase(ModelType.CLUSTER.getModel())){
			basicConfig = getClusterConfig();
		}else if (model.equalsIgnoreCase(ModelType.CLOUD.getModel())){
			basicConfig = getCloudConfig();
		}
		RedissonInstancePool instancePool = new RedissonInstancePool(genericPoolConfig,basicConfig);
		RedissonInstanceSessionFactory sessionFactory = new RedissonInstanceSessionFactory();
		sessionFactory.setInstancePool(instancePool);
		return sessionFactory;
	}
	
	/**
	 *  获得 bean 对象.
	 */
	public RedissonSimpleConfig getSimpleConfig() {
		RedissonSimpleConfig instanceConfig = new RedissonSimpleConfig();
		GenericPool pool = redissonConfig.getPool();
		if (pool!=null){
			BeanUtils.copyProperties(pool,instanceConfig);
		}
		RedissonConfigProperties.Simple simple = redissonConfig.getSimple();
		if (simple!=null){
			BeanUtils.copyProperties(simple,instanceConfig);
		}else{
			logger.warn("单节点的配置信息为空,不能进行实例化操作.");
		}
		return instanceConfig;
	}
	
	/**
	 *  获得 bean 对象.
	 */
	public RedissonSentinelConfig getSentinelConfig() {
		RedissonSentinelConfig instanceConfig = new RedissonSentinelConfig();
		GenericPool pool = redissonConfig.getPool();
		if (pool!=null){
			BeanUtils.copyProperties(pool,instanceConfig);
		}
		RedissonConfigProperties.Sentinel sentinel = redissonConfig.getSentinel();
		if (sentinel!=null){
			BeanUtils.copyProperties(sentinel,instanceConfig);
		}else{
			logger.warn("哨兵模式的配置信息为空,不能进行实例化操作.");
		}
		return instanceConfig;
	}
	
	/**
	 *  获得 bean 对象.
	 */
	public RedissonMasterSlaveConfig getMasterSlaveConfig() {
		RedissonMasterSlaveConfig instanceConfig = new RedissonMasterSlaveConfig();
		GenericPool pool = redissonConfig.getPool();
		if (pool!=null){
			BeanUtils.copyProperties(pool,instanceConfig);
		}
		RedissonConfigProperties.MasterSlave masterSlave = redissonConfig.getMasterslave();
		if (masterSlave!=null){
			BeanUtils.copyProperties(masterSlave,instanceConfig);
		}else{
			logger.warn("主从模式的配置信息为空,不能进行实例化操作.");
		}
		return instanceConfig;
	}
	
	/**
	 *  获得 bean 对象.
	 */
	public RedissonClusterConfig getClusterConfig() {
		RedissonClusterConfig instanceConfig = new RedissonClusterConfig();
		GenericPool pool = redissonConfig.getPool();
		if (pool!=null){
			BeanUtils.copyProperties(pool,instanceConfig);
		}
		RedissonConfigProperties.Cluster cluster = redissonConfig.getCluster();
		if (cluster!=null){
			BeanUtils.copyProperties(cluster,instanceConfig);
		}else{
			logger.warn("集群模式的配置信息为空,不能进行实例化操作.");
		}
		return instanceConfig;
	}
	
	/**
	 *  获得 bean 对象.
	 */
	public RedissonCloudConfig getCloudConfig() {
		RedissonCloudConfig instanceConfig = new RedissonCloudConfig();
		GenericPool pool = redissonConfig.getPool();
		if (pool!=null){
			BeanUtils.copyProperties(pool,instanceConfig);
		}
		RedissonConfigProperties.Cloud cloud = redissonConfig.getCloud();
		if (cloud!=null){
			BeanUtils.copyProperties(cloud,instanceConfig);
		}else{
			logger.warn("云托管模式的配置信息为空,不能进行实例化操作.");
		}
		return instanceConfig;
	}
}
