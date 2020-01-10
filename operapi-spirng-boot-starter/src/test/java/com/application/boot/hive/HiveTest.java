package com.application.boot.hive;

import com.alibaba.fastjson.JSON;
import com.application.base.operapi.api.hive.factory.HiveJdbcSessionFactory;
import com.application.boot.OperApiTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author : 孤狼
 * @NAME: HiveTest
 * @DESC: HiveTest类设计
 **/
public class HiveTest extends OperApiTest {
	
	@Autowired
	private HiveJdbcSessionFactory sessionFactory;
	
	@Test
	public void test1(){
		LinkedList<Map<String,String>> info = sessionFactory.getJdbcSession().descTable("student");
		System.out.println("描述信息是: "+JSON.toJSONString(info));
	}

}
