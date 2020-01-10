package com.application.boot.operapi.core.api.hbase;

import com.application.base.operapi.api.hbase.config.HbaseConfig;
import com.application.base.operapi.api.hbase.factory.HbaseOperSessionFactory;
import com.application.base.operapi.api.hbase.pool.HbaseOperPool;
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
@ConditionalOnClass({HbaseOperSessionFactory.class})
// 将 application.properties 的相关的属性字段与该类一一对应，并生成 Bean
@EnableConfigurationProperties(HbaseConfigProperties.class)
public class HbaseAutoConfiguration {
	
	/**
	 * 属性的配置.
	 */
	@Autowired
	private HbaseConfigProperties hbaseProperties;
	
	/**
	 *  当容器没有这个 Bean 的时候才创建这个 Bean
	 */
	@Bean
	@ConditionalOnMissingBean(HbaseOperSessionFactory.class)
	public HbaseOperSessionFactory getHbaseSessionFactory() {
		HbaseConfig hbaseConfig = new HbaseConfig();
		GenericPool pool = hbaseProperties.getPool();
		BeanUtils.copyProperties(pool,hbaseConfig);
		hbaseConfig.setZookeeperQuorum(hbaseProperties.getZookeeperQuorum());
		hbaseConfig.setZookeeperPort(hbaseProperties.getZookeeperPort());
		hbaseConfig.setRootDir(hbaseProperties.getRootDir());
		hbaseConfig.setHadoopDir(hbaseProperties.getHadoopDir());
		hbaseConfig.setMaster(hbaseProperties.getMaster());
		hbaseConfig.setLoginUser(hbaseProperties.getLoginUser());
		hbaseConfig.setLoginPass(hbaseProperties.getLoginPass());
		
		HbaseOperPool hbaseOperPool = new HbaseOperPool(hbaseConfig);
		HbaseOperSessionFactory hbaseOperSessionFactory = new HbaseOperSessionFactory(hbaseOperPool);
		return hbaseOperSessionFactory;
	}
	
}
