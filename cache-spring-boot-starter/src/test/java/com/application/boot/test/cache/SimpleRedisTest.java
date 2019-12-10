package com.application.boot.test.cache;

import com.application.base.cache.redis.jedis.factory.simple.JedisSimpleSessionFactory;
import com.application.base.cache.redisson.redisson.factory.RedissonInstanceSessionFactory;
import com.application.boot.cache.redis.JedisConfigProperties;
import com.application.boot.cache.redisson.RedissonConfigProperties;
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
	private RedissonInstanceSessionFactory redissonFactory;
	
	@Autowired
	private JedisConfigProperties jedisConfig;
	
	@Autowired
	private RedissonConfigProperties redissonConfig;
	
	@Test
	public void test() {
		String instances = jedisConfig.getSimple().toString();
		System.out.println(instances);
		instances = redissonConfig.getSimple().toString();
		System.out.println(instances);
	}
	
}
