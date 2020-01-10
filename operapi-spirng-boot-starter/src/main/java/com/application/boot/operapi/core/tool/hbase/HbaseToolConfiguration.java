package com.application.boot.operapi.core.tool.hbase;

import com.application.base.operapi.tool.hbase.config.HbaseToolConfig;
import com.application.base.operapi.tool.hbase.core.impl.BasicHbaseClient;
import com.application.boot.operapi.core.api.hbase.HbaseConfigProperties;
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
// 当 BasicHbaseClient 在类路径的条件下
@ConditionalOnClass({BasicHbaseClient.class})
// 将 application.properties 的相关的属性字段与该类一一对应，并生成 Bean
@EnableConfigurationProperties(HbaseConfigProperties.class)
public class HbaseToolConfiguration {
	
	/**
	 * 属性的配置.
	 */
	@Autowired
	private HbaseConfigProperties hbaseProperties;
	
	/**
	 *  当容器没有这个 Bean 的时候才创建这个 Bean
	 */
	@Bean
	@ConditionalOnMissingBean(BasicHbaseClient.class)
	public BasicHbaseClient getBasicHbaseClient() {
		HbaseToolConfig toolConfig = new HbaseToolConfig();
		toolConfig.setZookeeperQuorum(hbaseProperties.getZookeeperQuorum());
		toolConfig.setZookeeperPort(hbaseProperties.getZookeeperPort());
		toolConfig.setRootDir(hbaseProperties.getRootDir());
		toolConfig.setHadoopDir(hbaseProperties.getHadoopDir());
		toolConfig.setMaster(hbaseProperties.getMaster());
		toolConfig.setLoginUser(hbaseProperties.getLoginUser());
		toolConfig.setLoginPass(hbaseProperties.getLoginPass());
		BasicHbaseClient basicHbaseClient = new BasicHbaseClient(toolConfig);
		return basicHbaseClient;
	}
	
}
