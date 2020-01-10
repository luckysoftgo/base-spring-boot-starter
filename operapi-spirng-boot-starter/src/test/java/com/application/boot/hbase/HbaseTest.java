package com.application.boot.hbase;

import com.alibaba.fastjson.JSON;
import com.application.base.operapi.api.hbase.factory.HbaseOperSessionFactory;
import com.application.base.operapi.tool.hbase.core.impl.BasicHbaseClient;
import com.application.boot.OperApiTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author : 孤狼
 * @NAME: HbaseTest
 * @DESC: HbaseTest类设计
 **/
public class HbaseTest extends OperApiTest {

	@Autowired
	private HbaseOperSessionFactory hbaseOperSessionFactory;
	@Autowired
	private BasicHbaseClient hbaseClient;
	
	@Test
	public void test1(){
		boolean result = hbaseOperSessionFactory.getHbaseSession().tableExist("tbl_abc");
		System.out.println("exstis="+result);
		String resultStr = hbaseOperSessionFactory.getHbaseSession().getValData("tbl_abc","rowKey1","cf1","value1");
		System.out.println("result="+resultStr);
	}
	
	@Test
	public void test2(){
		HbaseDemo ins = hbaseClient.get(1010001L, HbaseDemo.class);
		System.out.println("111111:"+JSON.toJSONString(ins));
	}
	
}
