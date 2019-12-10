package com.application.boot.cache.redis;

import com.application.boot.cache.common.GenericPool;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 孤狼
 * @NAME: EsRestClientConfigProperties
 * @DESC: 属性获取 https://blog.csdn.net/qq_32924343/article/details/100108734
 **/
@ConfigurationProperties(prefix = "redis")
public class JedisConfigProperties {
	/**
	 * 连接池对象
	 */
	private GenericPool pool =new GenericPool();
	
	/**
	 * 单机登录
	 */
	private Simple simple =new Simple();
	
	/**
	 * 哨兵
	 */
	private Sentinel sentinel =new Sentinel();
	
	/**
	 * 集群信息
	 */
	private Cluster cluster =new Cluster();
	
	/**
	 * 分片信息
	 */
	private Sharded sharded =new Sharded();
	
	/**
	 * 哨兵分片信息
	 */
	private Complex complex =new Complex();
	
	public GenericPool getPool() {
		return pool;
	}
	
	public void setPool(GenericPool pool) {
		this.pool = pool;
	}
	
	public Simple getSimple() {
		return simple;
	}
	
	public void setSimple(Simple simple) {
		this.simple = simple;
	}
	
	public Sentinel getSentinel() {
		return sentinel;
	}
	
	public void setSentinel(Sentinel sentinel) {
		this.sentinel = sentinel;
	}
	
	public Cluster getCluster() {
		return cluster;
	}
	
	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}
	
	public Sharded getSharded() {
		return sharded;
	}
	
	public void setSharded(Sharded sharded) {
		this.sharded = sharded;
	}
	
	public Complex getComplex() {
		return complex;
	}
	
	public void setComplex(Complex complex) {
		this.complex = complex;
	}
	
	/**
	 * 哨兵模式.
	 */
	public static class Sentinel {
		/**
		 * ip 和 端口 的映射.
		 */
		private List<IpPortInfo> sentinels = new ArrayList<>();
		/**
		 * 哨兵 master
		 */
		private String mastername;
		/**
		 * seninel 串信息: 22.15.69.89.94:16339;22.15.69.89.95:16339;22.15.69.89.96:16339
		 */
		private String instances;
		/**
		 * 连接超时时间.
		 */
		private Integer timeout;
		
		public List<IpPortInfo> getSentinels() {
			return sentinels;
		}
		
		public void setSentinels(List<IpPortInfo> sentinels) {
			this.sentinels = sentinels;
		}
		
		public String getMastername() {
			return mastername;
		}
		
		public void setMastername(String mastername) {
			this.mastername = mastername;
		}
		
		public String getInstances() {
			return instances;
		}
		
		public void setInstances(String instances) {
			this.instances = instances;
		}
		
		public Integer getTimeout() {
			return timeout;
		}
		
		public void setTimeout(Integer timeout) {
			this.timeout = timeout;
		}
		
		@Override
		public String toString(){
			return "sentinels="+sentinels.toString()+",mastername="+mastername+",instances="+instances+",timeout="+timeout;
		}
	}
	
	/**
	 * 登录的用户名密码.
	 */
	public static class IpPortInfo {
		/**
		 * ip端口信息:127.0.0.1:6379
		 */
		private String info;
		
		public String getInfo() {
			return info;
		}
		
		public void setInfo(String info) {
			this.info = info;
		}
		
		@Override
		public String toString(){
			return "info="+info;
		}
	}
	
	/**
	 * 单机登录.
	 */
	public static class Simple {
		/**
		 * 主机信息
		 */
		private String host;
		/**
		 * 端口
		 */
		private Integer port;
		/**
		 * redis 库
		 */
		private Integer database;
		/**
		 * 登录密码.
		 */
		private String password;
		/**
		 * 连接超时时间.
		 */
		private Integer timeout;
		
		public String getHost() {
			return host;
		}
		
		public void setHost(String host) {
			this.host = host;
		}
		
		public Integer getPort() {
			return port;
		}
		
		public void setPort(Integer port) {
			this.port = port;
		}
		
		public Integer getDatabase() {
			return database;
		}
		
		public void setDatabase(Integer database) {
			this.database = database;
		}
		
		public String getPassword() {
			return password;
		}
		
		public void setPassword(String password) {
			this.password = password;
		}
		
		public Integer getTimeout() {
			return timeout;
		}
		
		public void setTimeout(Integer timeout) {
			this.timeout = timeout;
		}
		
		@Override
		public String toString(){
			return "host="+host+",port="+port+",database="+database+",password="+password+",timeout="+timeout;
		}
	}
	
	/**
	 * 集群设置.
	 */
	public static class Cluster {
		/**
		 * 主机信息: 22.15.69.89.94:16339;22.15.69.89.95:16339;22.15.69.89.96:16339
		 */
		private String instances;
		/**
		 * 超时时间
		 */
		private Integer sotimeout;
		/**
		 * 最大连接次
		 */
		private Integer maxattempts;
		/**
		 * 登录密码.
		 */
		private String password;
		/**
		 * 连接超时时间.
		 */
		private Integer timeout;
		
		public String getInstances() {
			return instances;
		}
		
		public void setInstances(String instances) {
			this.instances = instances;
		}
		
		public Integer getSotimeout() {
			return sotimeout;
		}
		
		public void setSotimeout(Integer sotimeout) {
			this.sotimeout = sotimeout;
		}
		
		public Integer getMaxattempts() {
			return maxattempts;
		}
		
		public void setMaxattempts(Integer maxattempts) {
			this.maxattempts = maxattempts;
		}
		
		public String getPassword() {
			return password;
		}
		
		public void setPassword(String password) {
			this.password = password;
		}
		
		public Integer getTimeout() {
			return timeout;
		}
		
		public void setTimeout(Integer timeout) {
			this.timeout = timeout;
		}
		
		@Override
		public String toString(){
			return "instances="+instances+",sotimeout="+sotimeout+",maxattempts="+maxattempts+",password="+password+",timeout="+timeout;
		}
	}
	
	/**
	 * 分片.
	 */
	public static class Sharded {
		/**
		 * 主机信息: 22.15.69.89.94:16339;22.15.69.89.95:16339;22.15.69.89.96:16339
		 */
		private String instances;
		/**
		 * 超时时间
		 */
		private Integer sotimeout;
		/**
		 * 最大连接次
		 */
		private Integer maxattempts;
		/**
		 * 登录密码.
		 */
		private String password;
		/**
		 * 连接超时时间.
		 */
		private Integer timeout;
		
		public String getInstances() {
			return instances;
		}
		
		public void setInstances(String instances) {
			this.instances = instances;
		}
		
		public Integer getSotimeout() {
			return sotimeout;
		}
		
		public void setSotimeout(Integer sotimeout) {
			this.sotimeout = sotimeout;
		}
		
		public Integer getMaxattempts() {
			return maxattempts;
		}
		
		public void setMaxattempts(Integer maxattempts) {
			this.maxattempts = maxattempts;
		}
		
		public String getPassword() {
			return password;
		}
		
		public void setPassword(String password) {
			this.password = password;
		}
		
		public Integer getTimeout() {
			return timeout;
		}
		
		public void setTimeout(Integer timeout) {
			this.timeout = timeout;
		}
		
		@Override
		public String toString(){
			return "instances="+instances+",sotimeout="+sotimeout+",maxattempts="+maxattempts+",password="+password+",timeout="+timeout;
		}
	}
	
	/**
	 * 哨兵分片方式.
	 */
	public static class Complex {
		/**
		 * 连接超时时间.
		 */
		private Integer timeout;
		
		/**
		 * master 信息
		 */
		private List<IpPortInfo> masters = new ArrayList<>();
		
		/**
		 * sentinel 信息
		 */
		private List<IpPortInfo> sentinels = new ArrayList<>();
		
		/**
		 * 登录密码.
		 */
		private String password;
		
		public Integer getTimeout() {
			return timeout;
		}
		
		public void setTimeout(Integer timeout) {
			this.timeout = timeout;
		}
		
		public List<IpPortInfo> getMasters() {
			return masters;
		}
		
		public void setMasters(List<IpPortInfo> masters) {
			this.masters = masters;
		}
		
		public List<IpPortInfo> getSentinels() {
			return sentinels;
		}
		
		public void setSentinels(List<IpPortInfo> sentinels) {
			this.sentinels = sentinels;
		}
		
		public String getPassword() {
			return password;
		}
		
		public void setPassword(String password) {
			this.password = password;
		}
		
		@Override
		public String toString(){
			return "masters="+masters+",sentinels="+sentinels+",password="+password+",timeout="+timeout;
		}
	}
}
