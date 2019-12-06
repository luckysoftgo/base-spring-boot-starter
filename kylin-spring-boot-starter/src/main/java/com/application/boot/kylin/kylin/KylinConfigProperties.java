package com.application.boot.kylin.kylin;

import com.application.boot.kylin.common.GenericPool;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : 孤狼
 * @NAME: EsRestClientConfigProperties
 * @DESC: 属性获取 https://blog.csdn.net/qq_32924343/article/details/100108734
 **/
@ConfigurationProperties(prefix = "kylin")
public class KylinConfigProperties {
	/**
	 * 连接池对象
	 */
	private GenericPool pool =new GenericPool();
	
	/**
	 * 登录
	 */
	private KylinLogin login =new KylinLogin();
	
	/**
	 * rest api 信息
	 */
	private RestApi rest =new RestApi();
	
	/**
	 * jdbc 信息
	 */
	private JdbcApi jdbc =new JdbcApi();
	
	public GenericPool getPool() {
		return pool;
	}
	
	public void setPool(GenericPool pool) {
		this.pool = pool;
	}
	
	public KylinLogin getLogin() {
		return login;
	}
	
	public void setLogin(KylinLogin login) {
		this.login = login;
	}
	
	public RestApi getRest() {
		return rest;
	}
	
	public void setRest(RestApi rest) {
		this.rest = rest;
	}
	
	public JdbcApi getJdbc() {
		return jdbc;
	}
	
	public void setJdbc(JdbcApi jdbc) {
		this.jdbc = jdbc;
	}
	
	/**
	 * rest 会话
	 */
	public static class RestApi {
		/**
		 * host 配置信息
		 */
		private String host;
		
		public String getHost() {
			return host;
		}
		
		public void setHost(String host) {
			this.host = host;
		}
	}
	
	/**
	 * jdbc 会话
	 */
	public static class JdbcApi {
		/**
		 * url 配置信息
		 */
		private String url;
		
		public String getUrl() {
			return url;
		}
		
		public void setUrl(String url) {
			this.url = url;
		}
	}
	
	/**
	 * 登录信息.
	 */
	public static class KylinLogin {
		/**
		 * 用户名
		 */
		private String name;
		/**
		 * 密码
		 */
		private String pass;
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getPass() {
			return pass;
		}
		
		public void setPass(String pass) {
			this.pass = pass;
		}
	}
}
