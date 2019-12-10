package com.application.boot.cache.redisson;

import com.application.boot.cache.common.GenericPool;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : 孤狼
 * @NAME: EsRestClientConfigProperties
 * @DESC: 属性获取 https://blog.csdn.net/qq_32924343/article/details/100108734
 **/
@ConfigurationProperties(prefix = "redisson")
public class RedissonConfigProperties {
	/**
	 * 模式类型:simple,sentinel,masterslave,cluster,cloud
	 */
	private String model;
	/**
	 * 连接池对象
	 */
	private GenericPool pool =new GenericPool();
	/**
	 * 单机登录
	 */
	private Simple simple =new Simple();
	/**
	 * 哨兵模式
	 */
	private Sentinel sentinel =new Sentinel();
	/**
	 * 主从模式
	 */
	private MasterSlave masterslave = new MasterSlave();
	/**
	 * 集群模式.
	 */
	private Cluster cluster = new Cluster();
	/**
	 * 云托管.
	 */
	private Cloud cloud = new Cloud();
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
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
	
	public MasterSlave getMasterslave() {
		return masterslave;
	}
	
	public void setMasterslave(MasterSlave masterslave) {
		this.masterslave = masterslave;
	}
	
	public Cluster getCluster() {
		return cluster;
	}
	
	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}
	
	public Cloud getCloud() {
		return cloud;
	}
	
	public void setCloud(Cloud cloud) {
		this.cloud = cloud;
	}
	
	/**
	 * 哨兵模式.
	 */
	public static class Sentinel {
		/**
		 * master size
		 */
		private Integer masterConnectionPoolSize=500;
		/**
		 * slave size
		 */
		private Integer slaveConnectionPoolSize=500;
		/**
		 * 连接数
		 */
		private Integer idleConnectionTimeout=1000;
		
		private Integer connectTimeout=5000;
		
		private Integer timeout=3000;
		
		private Integer pingTimeout=30000;
		
		private Integer reconnectionTimeout=3000;
		/**
		 * 连接串
		 */
		private String instances="redis://127.0.0.1:6379;redis://127.0.0.1:26379";
		/**
		 * 主节点名称.
		 */
		private String masterName="prd01";
		
		public Integer getMasterConnectionPoolSize() {
			return masterConnectionPoolSize;
		}
		
		public void setMasterConnectionPoolSize(Integer masterConnectionPoolSize) {
			this.masterConnectionPoolSize = masterConnectionPoolSize;
		}
		
		public Integer getSlaveConnectionPoolSize() {
			return slaveConnectionPoolSize;
		}
		
		public void setSlaveConnectionPoolSize(Integer slaveConnectionPoolSize) {
			this.slaveConnectionPoolSize = slaveConnectionPoolSize;
		}
		
		public Integer getIdleConnectionTimeout() {
			return idleConnectionTimeout;
		}
		
		public void setIdleConnectionTimeout(Integer idleConnectionTimeout) {
			this.idleConnectionTimeout = idleConnectionTimeout;
		}
		
		public Integer getConnectTimeout() {
			return connectTimeout;
		}
		
		public void setConnectTimeout(Integer connectTimeout) {
			this.connectTimeout = connectTimeout;
		}
		
		public Integer getTimeout() {
			return timeout;
		}
		
		public void setTimeout(Integer timeout) {
			this.timeout = timeout;
		}
		
		public Integer getPingTimeout() {
			return pingTimeout;
		}
		
		public void setPingTimeout(Integer pingTimeout) {
			this.pingTimeout = pingTimeout;
		}
		
		public Integer getReconnectionTimeout() {
			return reconnectionTimeout;
		}
		
		public void setReconnectionTimeout(Integer reconnectionTimeout) {
			this.reconnectionTimeout = reconnectionTimeout;
		}
		
		public String getInstances() {
			return instances;
		}
		
		public void setInstances(String instances) {
			this.instances = instances;
		}
		
		public String getMasterName() {
			return masterName;
		}
		
		public void setMasterName(String masterName) {
			this.masterName = masterName;
		}
		
		@Override
		public String toString(){
			return "masterConnectionPoolSize="+masterConnectionPoolSize+",slaveConnectionPoolSize="+slaveConnectionPoolSize+",idleConnectionTimeout="+idleConnectionTimeout+",connectTimeout="+connectTimeout+
					",timeout="+timeout+",pingTimeout="+pingTimeout+",reconnectionTimeout="+reconnectionTimeout+",instances="+instances+",masterName="+masterName;
		}
	}
	
	/**
	 * 主从模式.
	 */
	public static class MasterSlave {
		/**
		 * master size
		 */
		private Integer masterConnectionPoolSize=500;
		/**
		 * slave size
		 */
		private Integer slaveConnectionPoolSize=500;
		/**
		 * 连接数
		 */
		private Integer idleConnectionTimeout=1000;
		
		private Integer connectTimeout=5000;
		
		private Integer timeout=3000;
		
		private Integer pingTimeout=30000;
		
		private Integer reconnectionTimeout=3000;
		/**
		 * 连接串
		 */
		private String instances="redis://127.0.0.1:6379;redis://127.0.0.1:26379";
		/**
		 * 主节点地址
		 */
		private String masterAddr="redis://127.0.0.1:6379";
		
		public Integer getMasterConnectionPoolSize() {
			return masterConnectionPoolSize;
		}
		
		public void setMasterConnectionPoolSize(Integer masterConnectionPoolSize) {
			this.masterConnectionPoolSize = masterConnectionPoolSize;
		}
		
		public Integer getSlaveConnectionPoolSize() {
			return slaveConnectionPoolSize;
		}
		
		public void setSlaveConnectionPoolSize(Integer slaveConnectionPoolSize) {
			this.slaveConnectionPoolSize = slaveConnectionPoolSize;
		}
		
		public Integer getIdleConnectionTimeout() {
			return idleConnectionTimeout;
		}
		
		public void setIdleConnectionTimeout(Integer idleConnectionTimeout) {
			this.idleConnectionTimeout = idleConnectionTimeout;
		}
		
		public Integer getConnectTimeout() {
			return connectTimeout;
		}
		
		public void setConnectTimeout(Integer connectTimeout) {
			this.connectTimeout = connectTimeout;
		}
		
		public Integer getTimeout() {
			return timeout;
		}
		
		public void setTimeout(Integer timeout) {
			this.timeout = timeout;
		}
		
		public Integer getPingTimeout() {
			return pingTimeout;
		}
		
		public void setPingTimeout(Integer pingTimeout) {
			this.pingTimeout = pingTimeout;
		}
		
		public Integer getReconnectionTimeout() {
			return reconnectionTimeout;
		}
		
		public void setReconnectionTimeout(Integer reconnectionTimeout) {
			this.reconnectionTimeout = reconnectionTimeout;
		}
		
		public String getInstances() {
			return instances;
		}
		
		public void setInstances(String instances) {
			this.instances = instances;
		}
		
		public String getMasterAddr() {
			return masterAddr;
		}
		
		public void setMasterAddr(String masterAddr) {
			this.masterAddr = masterAddr;
		}
		
		@Override
		public String toString(){
			return "masterConnectionPoolSize="+masterConnectionPoolSize+",slaveConnectionPoolSize="+slaveConnectionPoolSize+",idleConnectionTimeout="+idleConnectionTimeout+",connectTimeout="+connectTimeout+
					",timeout="+timeout+",pingTimeout="+pingTimeout+",reconnectionTimeout="+reconnectionTimeout+",instances="+instances+",masterAddr="+masterAddr;
		}
	}
	
	/**
	 * 单机登录.
	 */
	public static class Simple {
		/**
		 * 连接池大小
		 */
		private Integer connectionPoolSize=500;
		/**
		 * 失效时
		 */
		private Integer idleConnectionTimeout=1000;
		/**
		 * 连接失效时间
		 */
		private Integer connectTimeout=5000;
		/**
		 *超时
		 */
		private Integer timeout=3000;
		
		private Integer pingTimeout=30000;
		/**
		 * 重连时间
		 */
		private Integer reconnectionTimeout=3000;
		/**
		 * 连接地址
		 */
		private String address="redis://127.0.0.1:6379";
		
		public Integer getConnectionPoolSize() {
			return connectionPoolSize;
		}
		
		public void setConnectionPoolSize(Integer connectionPoolSize) {
			this.connectionPoolSize = connectionPoolSize;
		}
		
		public Integer getIdleConnectionTimeout() {
			return idleConnectionTimeout;
		}
		
		public void setIdleConnectionTimeout(Integer idleConnectionTimeout) {
			this.idleConnectionTimeout = idleConnectionTimeout;
		}
		
		public Integer getConnectTimeout() {
			return connectTimeout;
		}
		
		public void setConnectTimeout(Integer connectTimeout) {
			this.connectTimeout = connectTimeout;
		}
		
		public Integer getTimeout() {
			return timeout;
		}
		
		public void setTimeout(Integer timeout) {
			this.timeout = timeout;
		}
		
		public Integer getPingTimeout() {
			return pingTimeout;
		}
		
		public void setPingTimeout(Integer pingTimeout) {
			this.pingTimeout = pingTimeout;
		}
		
		public Integer getReconnectionTimeout() {
			return reconnectionTimeout;
		}
		
		public void setReconnectionTimeout(Integer reconnectionTimeout) {
			this.reconnectionTimeout = reconnectionTimeout;
		}
		
		public String getAddress() {
			return address;
		}
		
		public void setAddress(String address) {
			this.address = address;
		}
		
		@Override
		public String toString(){
			return "connectionPoolSize="+connectionPoolSize+",idleConnectionTimeout="+idleConnectionTimeout+",connectTimeout="+connectTimeout+",pingTimeout="+pingTimeout+",timeout="+timeout+",address="+address;
		}
	}
	
	/**
	 * 集群设置.
	 */
	public static class Cluster {
		/**
		 * master size
		 */
		private Integer masterConnectionPoolSize=500;
		/**
		 * slave size
		 */
		private Integer slaveConnectionPoolSize=500;
		/**
		 * 连接数
		 */
		private Integer idleConnectionTimeout=1000;
		
		private Integer connectTimeout=5000;
		
		private Integer timeout=3000;
		
		private Integer pingTimeout=30000;
		
		private Integer reconnectionTimeout=3000;
		/**
		 * 连接串
		 */
		private String instances="redis://127.0.0.1:6379;redis://127.0.0.1:26379";
	
		public Integer getMasterConnectionPoolSize() {
			return masterConnectionPoolSize;
		}
		
		public void setMasterConnectionPoolSize(Integer masterConnectionPoolSize) {
			this.masterConnectionPoolSize = masterConnectionPoolSize;
		}
		
		public Integer getSlaveConnectionPoolSize() {
			return slaveConnectionPoolSize;
		}
		
		public void setSlaveConnectionPoolSize(Integer slaveConnectionPoolSize) {
			this.slaveConnectionPoolSize = slaveConnectionPoolSize;
		}
		
		public Integer getIdleConnectionTimeout() {
			return idleConnectionTimeout;
		}
		
		public void setIdleConnectionTimeout(Integer idleConnectionTimeout) {
			this.idleConnectionTimeout = idleConnectionTimeout;
		}
		
		public Integer getConnectTimeout() {
			return connectTimeout;
		}
		
		public void setConnectTimeout(Integer connectTimeout) {
			this.connectTimeout = connectTimeout;
		}
		
		public Integer getTimeout() {
			return timeout;
		}
		
		public void setTimeout(Integer timeout) {
			this.timeout = timeout;
		}
		
		public Integer getPingTimeout() {
			return pingTimeout;
		}
		
		public void setPingTimeout(Integer pingTimeout) {
			this.pingTimeout = pingTimeout;
		}
		
		public Integer getReconnectionTimeout() {
			return reconnectionTimeout;
		}
		
		public void setReconnectionTimeout(Integer reconnectionTimeout) {
			this.reconnectionTimeout = reconnectionTimeout;
		}
		
		public String getInstances() {
			return instances;
		}
		
		public void setInstances(String instances) {
			this.instances = instances;
		}
		
		@Override
		public String toString(){
			return "masterConnectionPoolSize="+masterConnectionPoolSize+",slaveConnectionPoolSize="+slaveConnectionPoolSize+",idleConnectionTimeout="+idleConnectionTimeout+",connectTimeout="+connectTimeout+
					",timeout="+timeout+",pingTimeout="+pingTimeout+",reconnectionTimeout="+reconnectionTimeout+",instances="+instances;
		}
		
	}
	
	/**
	 * 云托管设置.
	 */
	public static class Cloud {
		/**
		 * master size
		 */
		private Integer masterConnectionPoolSize=500;
		/**
		 * slave size
		 */
		private Integer slaveConnectionPoolSize=500;
		/**
		 * 连接数
		 */
		private Integer idleConnectionTimeout=1000;
		
		private Integer connectTimeout=5000;
		
		private Integer timeout=3000;
		
		private Integer pingTimeout=30000;
		
		private Integer reconnectionTimeout=3000;
		/**
		 * 连接串
		 */
		private String instances="redis://127.0.0.1:6379;redis://127.0.0.1:26379";
		
		public Integer getMasterConnectionPoolSize() {
			return masterConnectionPoolSize;
		}
		
		public void setMasterConnectionPoolSize(Integer masterConnectionPoolSize) {
			this.masterConnectionPoolSize = masterConnectionPoolSize;
		}
		
		public Integer getSlaveConnectionPoolSize() {
			return slaveConnectionPoolSize;
		}
		
		public void setSlaveConnectionPoolSize(Integer slaveConnectionPoolSize) {
			this.slaveConnectionPoolSize = slaveConnectionPoolSize;
		}
		
		public Integer getIdleConnectionTimeout() {
			return idleConnectionTimeout;
		}
		
		public void setIdleConnectionTimeout(Integer idleConnectionTimeout) {
			this.idleConnectionTimeout = idleConnectionTimeout;
		}
		
		public Integer getConnectTimeout() {
			return connectTimeout;
		}
		
		public void setConnectTimeout(Integer connectTimeout) {
			this.connectTimeout = connectTimeout;
		}
		
		public Integer getTimeout() {
			return timeout;
		}
		
		public void setTimeout(Integer timeout) {
			this.timeout = timeout;
		}
		
		public Integer getPingTimeout() {
			return pingTimeout;
		}
		
		public void setPingTimeout(Integer pingTimeout) {
			this.pingTimeout = pingTimeout;
		}
		
		public Integer getReconnectionTimeout() {
			return reconnectionTimeout;
		}
		
		public void setReconnectionTimeout(Integer reconnectionTimeout) {
			this.reconnectionTimeout = reconnectionTimeout;
		}
		
		public String getInstances() {
			return instances;
		}
		
		public void setInstances(String instances) {
			this.instances = instances;
		}
		
		@Override
		public String toString(){
			return "masterConnectionPoolSize="+masterConnectionPoolSize+",slaveConnectionPoolSize="+slaveConnectionPoolSize+",idleConnectionTimeout="+idleConnectionTimeout+",connectTimeout="+connectTimeout+
					",timeout="+timeout+",pingTimeout="+pingTimeout+",reconnectionTimeout="+reconnectionTimeout+",instances="+instances;
		}
		
	}
}
