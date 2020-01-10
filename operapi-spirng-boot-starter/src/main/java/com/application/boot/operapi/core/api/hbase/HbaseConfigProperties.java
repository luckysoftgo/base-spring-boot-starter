package com.application.boot.operapi.core.api.hbase;

import com.application.boot.operapi.common.GenericPool;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : 孤狼
 * @NAME: HiveConfigProperties
 * @DESC: HbaseConfigProperties类设计
 **/
@ConfigurationProperties(prefix = "hbase")
public class HbaseConfigProperties {
	
	/**
	 * 连接池对象
	 */
	private GenericPool pool =new GenericPool();
	/**
	 *D://installer/hadoop-2.7.7
	 */
	private String hadoopDir;
	/**
	 * hdfs://192.168.10.185:8020/hbase
	 */
	private String rootDir;
	/**
	 * 192.168.10.125
	 */
	private String zookeeperQuorum;
	/**
	 * 2181
	 */
	private String zookeeperPort;
	/**
	 * 登录的用户名.
	 */
	public String loginUser;
	
	/**
	 * 登录的密码.
	 */
	public String loginPass;
	/**
	 * 主机设置.
	 */
	public String master;
	
	public GenericPool getPool() {
		return pool;
	}
	
	public void setPool(GenericPool pool) {
		this.pool = pool;
	}
	
	public String getHadoopDir() {
		return hadoopDir;
	}
	
	public void setHadoopDir(String hadoopDir) {
		this.hadoopDir = hadoopDir;
	}
	
	public String getRootDir() {
		return rootDir;
	}
	
	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}
	
	public String getZookeeperQuorum() {
		return zookeeperQuorum;
	}
	
	public void setZookeeperQuorum(String zookeeperQuorum) {
		this.zookeeperQuorum = zookeeperQuorum;
	}
	
	public String getZookeeperPort() {
		return zookeeperPort;
	}
	
	public void setZookeeperPort(String zookeeperPort) {
		this.zookeeperPort = zookeeperPort;
	}
	
	public String getLoginUser() {
		return loginUser;
	}
	
	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}
	
	public String getLoginPass() {
		return loginPass;
	}
	
	public void setLoginPass(String loginPass) {
		this.loginPass = loginPass;
	}
	
	public String getMaster() {
		return master;
	}
	
	public void setMaster(String master) {
		this.master = master;
	}
}
