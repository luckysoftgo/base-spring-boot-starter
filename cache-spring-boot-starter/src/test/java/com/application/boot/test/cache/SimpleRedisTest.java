package com.application.boot.test.cache;

import com.application.base.cache.redis.jedis.factory.simple.JedisSimpleSessionFactory;
import com.application.boot.cache.redis.JedisConfigProperties;
import com.application.boot.test.BasicStartTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author : 孤狼
 * @NAME: SimpleRedisTest
 * @DESC:
 **/
public class SimpleRedisTest extends BasicStartTest {
	
	@Autowired
	private JedisSimpleSessionFactory redisFactory;
	
	@Autowired
	private JedisConfigProperties jedisConfig;
	
	@Test
	public void test() {
		String instances = jedisConfig.getSimple().toString();
		System.out.println(instances);
	}
	
}
