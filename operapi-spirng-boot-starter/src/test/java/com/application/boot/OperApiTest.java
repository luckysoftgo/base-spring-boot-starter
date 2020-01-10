package com.application.boot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : 孤狼
 * @NAME: OperApiTest
 * @DESC: 测试
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootStartApplication.class)
public class OperApiTest {

	@Test
	public void test(){
		System.out.println("basic test running!!");
	}
}