package com.application.boot.elastic.core;

import com.application.base.elastic.elastic.restclient.config.EsRestClientPoolConfig;
import com.application.base.elastic.elastic.restclient.factory.EsRestClientSessionPoolFactory;
import com.application.base.elastic.elastic.restclient.pool.ElasticRestClientPool;
import com.application.base.elastic.elastic.transport.config.EsTransportPoolConfig;
import com.application.base.elastic.elastic.transport.factory.EsTransportSessionPoolFactory;
import com.application.base.elastic.elastic.transport.pool.ElasticTransportPool;
import com.application.base.utils.json.JsonConvertUtils;
import com.application.boot.elastic.common.GenericPool;
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

/**
 * @author : 孤狼
 * @NAME: EsRestClientAutoConfiguration
 * @DESC: 装配对象参数.
 **/
// 相当于一个普通的 java 配置类
@Configuration
//当EsRestClientSessionPoolFactory,EsTransportSessionPoolFactory 在类路径的条件下
@ConditionalOnClass({EsTransportSessionPoolFactory.class, EsRestClientSessionPoolFactory.class})
//将 application.properties 的相关的属性字段与该类一一对应，并生成 Bean
@EnableConfigurationProperties(ElasticSearchConfigProperties.class)
public class ElasticSearchAutoConfiguration {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 属性的配置.
	 */
	@Autowired
	private ElasticSearchConfigProperties elasticSearchConfig;
	
	/**
	 *  当容器没有这个 Bean 的时候才创建这个 Bean
	 */
	@Bean
	@ConditionalOnMissingBean(EsRestClientSessionPoolFactory.class)
	public EsRestClientSessionPoolFactory getEsRestClientFactory() {
		if (StringUtils.isBlank(elasticSearchConfig.getRestcientServerInfos())){
			logger.info("elasticSearch get restclient settings is null, properties is :{}", JsonConvertUtils.toJson(elasticSearchConfig));
			return null;
		}
		EsRestClientPoolConfig poolConfig = new EsRestClientPoolConfig(elasticSearchConfig.getClusterName(),elasticSearchConfig.getRestcientServerInfos(),elasticSearchConfig.getAuthLogin());
		GenericPool pool = elasticSearchConfig.getPool();
		BeanUtils.copyProperties(pool,poolConfig);
		ElasticRestClientPool restClientPool = new ElasticRestClientPool(poolConfig);
		EsRestClientSessionPoolFactory restClientSessionFactory = new EsRestClientSessionPoolFactory(restClientPool);
		return restClientSessionFactory;
	}
	
	/**
	 *  当容器没有这个 Bean 的时候才创建这个 Bean
	 */
	@Bean
	@ConditionalOnMissingBean(EsTransportSessionPoolFactory.class)
	public EsTransportSessionPoolFactory getEsTransportFactory() {
		if (StringUtils.isBlank(elasticSearchConfig.getTransportServerInfos())){
			logger.info("elasticSearch get transport settings is null, properties is :{}", JsonConvertUtils.toJson(elasticSearchConfig));
			return null;
		}
		EsTransportPoolConfig poolConfig = new EsTransportPoolConfig(elasticSearchConfig.getClusterName(),elasticSearchConfig.getTransportServerInfos(),elasticSearchConfig.getAuthLogin());
		GenericPool pool = elasticSearchConfig.getPool();
		BeanUtils.copyProperties(pool,poolConfig);
		ElasticTransportPool transportPool = new ElasticTransportPool(poolConfig);
		EsTransportSessionPoolFactory transportSessionFactory = new EsTransportSessionPoolFactory(transportPool);
		return transportSessionFactory;
	}
	
}
