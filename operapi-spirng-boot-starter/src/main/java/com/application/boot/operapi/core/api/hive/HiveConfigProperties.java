package com.application.boot.operapi.core.api.hive;

import com.application.boot.operapi.common.GenericPool;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : 孤狼
 * @NAME: HiveConfigProperties
 * @DESC: HbaseConfigProperties类设计
 **/
@ConfigurationProperties(prefix = "hive")
public class HiveConfigProperties {
	
	/**
	 * 连接池对象
	 */
	private GenericPool pool =new GenericPool();
	/**
	 * 请求的用户名.
	 */
	private String userName;
	/**
	 * 请求的密码.
	 */
	private String userPass;
	/**
	 * driver名称.
	 */
	private String hiveDriver="org.apache.hive.jdbc.HiveDriver";
	/**
	 * hive的连接地址.
	 */
	private String hiveUrl;
	
	public GenericPool getPool() {
		return pool;
	}
	
	public void setPool(GenericPool pool) {
		this.pool = pool;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserPass() {
		return userPass;
	}
	
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	
	public String getHiveDriver() {
		return hiveDriver;
	}
	
	public void setHiveDriver(String hiveDriver) {
		this.hiveDriver = hiveDriver;
	}
	
	public String getHiveUrl() {
		return hiveUrl;
	}
	
	public void setHiveUrl(String hiveUrl) {
		this.hiveUrl = hiveUrl;
	}
}
