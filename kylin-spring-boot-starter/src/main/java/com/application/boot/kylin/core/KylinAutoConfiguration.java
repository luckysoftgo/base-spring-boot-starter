package com.application.boot.kylin.core;

import com.application.base.kylin.jdbc.config.KylinJdbcConfig;
import com.application.base.kylin.jdbc.factory.KylinJdbcSessionFactory;
import com.application.base.kylin.jdbc.pool.KylinJdbcOperPool;
import com.application.base.kylin.rest.config.KylinRestConfig;
import com.application.base.kylin.rest.factory.KylinJestSessionFactory;
import com.application.base.kylin.rest.pool.KylinJestApiPool;
import com.application.boot.kylin.common.GenericPool;
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
// 当 KylinJestSessionFactory,KylinJdbcSessionFactory 在类路径的条件下
@ConditionalOnClass({KylinJestSessionFactory.class, KylinJdbcSessionFactory.class})
// 将 application.properties 的相关的属性字段与该类一一对应，并生成 Bean
@EnableConfigurationProperties(KylinConfigProperties.class)
public class KylinAutoConfiguration {
	
	/**
	 * 属性的配置.
	 */
	@Autowired
	private KylinConfigProperties kylinConfig;
	
	/**
	 *  当容器没有这个 Bean 的时候才创建这个 Bean
	 */
	@Bean
	@ConditionalOnMissingBean(KylinJestSessionFactory.class)
	public KylinJestSessionFactory getKylinRestFactory() {
		KylinRestConfig restConfig = new KylinRestConfig();
		GenericPool pool = kylinConfig.getPool();
		BeanUtils.copyProperties(pool,restConfig);
		restConfig.setUserName(kylinConfig.getLogin().getName());
		restConfig.setUserPass(kylinConfig.getLogin().getPass());
		restConfig.setRequestUrl(kylinConfig.getRest().getHost());
		KylinJestApiPool kylinRestPool =new KylinJestApiPool(restConfig);
		KylinJestSessionFactory jestSessionFactory = new KylinJestSessionFactory(kylinRestPool);
		return jestSessionFactory;
	}
	
	/**
	 *  当容器没有这个 Bean 的时候才创建这个 Bean
	 */
	@Bean
	@ConditionalOnMissingBean(KylinJdbcSessionFactory.class)
	public KylinJdbcSessionFactory getKylinJdbcFactory() {
		KylinJdbcConfig jdbcConfig = new KylinJdbcConfig();
		GenericPool pool = kylinConfig.getPool();
		BeanUtils.copyProperties(pool,jdbcConfig);
		jdbcConfig.setUserName(kylinConfig.getLogin().getName());
		jdbcConfig.setUserPass(kylinConfig.getLogin().getPass());
		jdbcConfig.setKylinDriver(kylinConfig.getJdbc().getDriver());
		jdbcConfig.setKylinUrl(kylinConfig.getJdbc().getUrl());
		KylinJdbcOperPool kylinJdbcPool =new KylinJdbcOperPool(jdbcConfig);
		KylinJdbcSessionFactory jdbcSessionFactory = new KylinJdbcSessionFactory(kylinJdbcPool);
		return jdbcSessionFactory;
	}
}
