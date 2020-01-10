package com.application.boot.operapi.core.api.hive;

import com.application.base.operapi.api.hive.config.HiveJdbcConfig;
import com.application.base.operapi.api.hive.factory.HiveJdbcSessionFactory;
import com.application.base.operapi.api.hive.pool.HiveJdbcOperPool;
import com.application.boot.operapi.common.GenericPool;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author : 孤狼
 * @NAME: HbaseAutoConfiguration
 * @DESC: 装配对象参数.
 **/
// 相当于一个普通的 java 配置类
@Configuration
// 当 HbaseOperSessionFactory 在类路径的条件下
@ConditionalOnClass({HiveJdbcSessionFactory.class})
// 将 application.properties 的相关的属性字段与该类一一对应，并生成 Bean
@EnableConfigurationProperties(HiveConfigProperties.class)
public class HiveAutoConfiguration {
	
	/**
	 * 属性的配置.
	 */
	@Autowired
	private HiveConfigProperties hiveProperties;
	
	/**
	 *  当容器没有这个 Bean 的时候才创建这个 Bean
	 */
	@Bean
	@ConditionalOnMissingBean(HiveJdbcSessionFactory.class)
	public HiveJdbcSessionFactory getHiveSessionFactory() {
		HiveJdbcConfig hiveConfig = new HiveJdbcConfig();
		GenericPool pool = hiveProperties.getPool();
		BeanUtils.copyProperties(pool,hiveConfig);
		hiveConfig.setUserName(hiveProperties.getUserName());
		hiveConfig.setUserPass(hiveProperties.getUserPass());
		hiveConfig.setHiveDriver(hiveProperties.getHiveDriver());
		hiveConfig.setHiveUrl(hiveProperties.getHiveUrl());

		HiveJdbcOperPool hiveJdbcOperPool = new HiveJdbcOperPool(hiveConfig);
		HiveJdbcSessionFactory hiveSessionFactory = new HiveJdbcSessionFactory(hiveJdbcOperPool);
		return hiveSessionFactory;
	}
	
}
