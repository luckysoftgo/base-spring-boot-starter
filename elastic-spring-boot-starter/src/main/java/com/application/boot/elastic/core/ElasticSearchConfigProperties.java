package com.application.boot.elastic.core;

import com.application.boot.elastic.common.GenericPool;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : 孤狼
 * @NAME: EsRestClientConfigProperties
 * @DESC: 属性获取 https://blog.csdn.net/qq_32924343/article/details/100108734
 **/
@ConfigurationProperties(prefix = "elastic")
public class ElasticSearchConfigProperties {
	/**
	 * 连接池对象
	 */
	private GenericPool pool =new GenericPool();
	
	/**
	 * 登录信息:
	 */
	private String authLogin;
	
	/**
	 * Cluster 名称.
	 */
	private String clusterName;
	/**
	 * 节点配置信息
	 * restcient.serverInfos=192.168.1.1:9200,192.168.1.2:9200,192.168.1.3:9200
	 */
	private String restcientServerInfos="127.0.0.1:9200";
	/**
	 * 节点配置信息
	 * restcient.serverInfos=192.168.1.1:9300,192.168.1.2:9300,192.168.1.3:9300
	 */
	private String transportServerInfos="127.0.0.1:9300";
	
	public GenericPool getPool() {
		return pool;
	}
	
	public void setPool(GenericPool pool) {
		this.pool = pool;
	}
	
	public String getAuthLogin() {
		return authLogin;
	}
	
	public void setAuthLogin(String authLogin) {
		this.authLogin = authLogin;
	}
	
	public String getClusterName() {
		return clusterName;
	}
	
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	
	public String getRestcientServerInfos() {
		return restcientServerInfos;
	}
	
	public void setRestcientServerInfos(String restcientServerInfos) {
		this.restcientServerInfos = restcientServerInfos;
	}
	
	public String getTransportServerInfos() {
		return transportServerInfos;
	}
	
	public void setTransportServerInfos(String transportServerInfos) {
		this.transportServerInfos = transportServerInfos;
	}
}
