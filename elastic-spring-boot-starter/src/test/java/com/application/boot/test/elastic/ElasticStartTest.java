package com.application.boot.test.elastic;

import com.application.base.elastic.elastic.restclient.factory.EsRestClientSessionPoolFactory;
import com.application.base.elastic.elastic.transport.config.EsTransportNodeConfig;
import com.application.base.elastic.elastic.transport.config.EsTransportPoolConfig;
import com.application.base.elastic.elastic.transport.factory.EsTransportSessionPoolFactory;
import com.application.base.elastic.elastic.transport.pool.ElasticTransportPool;
import com.application.base.elastic.entity.ElasticData;
import com.application.base.utils.json.JsonConvertUtils;
import com.application.boot.elastic.common.GenericPool;
import com.application.boot.elastic.core.ElasticSearchConfigProperties;
import com.application.boot.test.BasicStartTest;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author : 孤狼
 * @NAME: ElasticStartTest
 * @DESC: es 测试
 **/
public class ElasticStartTest extends BasicStartTest {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ElasticSearchConfigProperties elasticSearchConfig;
	
	@Autowired
	private EsRestClientSessionPoolFactory restClient;
	
	@Autowired
	private EsTransportSessionPoolFactory transport;
	
	@Test
	public void test(){
		HashSet<EsTransportNodeConfig> esNodes = getTransportNodeInfos();
		EsTransportPoolConfig poolConfig = new EsTransportPoolConfig();
		if (StringUtils.isNotBlank(elasticSearchConfig.getCluster().getName())){
			poolConfig.setClusterName(elasticSearchConfig.getCluster().getName());
		}
		poolConfig.setEsNodes(esNodes);
		GenericPool pool = elasticSearchConfig.getPool();
		BeanUtils.copyProperties(pool,poolConfig);
		ElasticTransportPool transportPool = new ElasticTransportPool(poolConfig);
		EsTransportSessionPoolFactory transportSessionFactory = new EsTransportSessionPoolFactory(transportPool);
		for (int i = 0; i <500 ; i++) {
			ElasticData data = new ElasticData();
			data.setIndex("hahaha");
			data.setType("hahaha");
			data.setId("hahaha"+i);
			Map<String,Object> info=new HashMap<>();
			info.put("info1","测试"+i);
			info.put("info2","调试"+i);
			info.put("info3","上线"+i);
			info.put("info4","读写"+i);
			info.put("info5","分库"+i);
			data.setMapFlag(false);
			data.setData(JsonConvertUtils.toJson(info));
			boolean flag = transportSessionFactory.getElasticSession().addEsData(data);
			System.out.printf("index="+i+",flag="+flag);
		}
	}
	
	/**
	 * 获得 node 信息
	 * @return
	 */
	private HashSet<EsTransportNodeConfig> getTransportNodeInfos(){
		HashSet<EsTransportNodeConfig> nodeConfigs = new HashSet<>();
		List<ElasticSearchConfigProperties.ConnectClientInfo> transportInfos = elasticSearchConfig.getTransport();
		if (transportInfos!=null && transportInfos.size()>0){
			for (ElasticSearchConfigProperties.ConnectClientInfo transportInfo : transportInfos) {
				EsTransportNodeConfig nodeConfig = new EsTransportNodeConfig();
				nodeConfig.setNodeName(transportInfo.getName());
				nodeConfig.setNodeHost(transportInfo.getHost());
				nodeConfig.setNodePort(transportInfo.getPort());
				nodeConfig.setNodeSchema(transportInfo.getSchema());
				nodeConfigs.add(nodeConfig);
			}
		}
		return nodeConfigs;
	}
	
	
	
	@Test
	public void restClient(){
		logger.info("采用 resclient 服务放数据 start !");
		for (int i = 0; i <500 ; i++) {
			ElasticData data = new ElasticData();
			data.setIndex("hahaha");
			data.setType("hahaha");
			data.setId("hahaha"+i);
			Map<String,Object> info=new HashMap<>();
			info.put("info1","测试"+i);
			info.put("info2","调试"+i);
			info.put("info3","上线"+i);
			info.put("info4","读写"+i);
			info.put("info5","分库"+i);
			data.setMapFlag(false);
			data.setData(JsonConvertUtils.toJson(info));
			boolean flag = restClient.getElasticSession().addEsData(data);
			System.out.println("index="+i+",flag="+flag);
		}
		logger.info("采用 resclient 服务放数据 end !");
	}
	
	@Test
	public void transport(){
		logger.info("采用 transport 服务放数据 start !");
		try {
			for (int i = 500; i <1000 ; i++) {
				ElasticData data = new ElasticData();
				data.setIndex("hahaha");
				data.setType("hahaha");
				data.setId("hahaha"+i);
				Map<String,Object> info=new HashMap<>();
				info.put("info1","测试"+i);
				info.put("info2","调试"+i);
				info.put("info3","上线"+i);
				info.put("info4","读写"+i);
				info.put("info5","分库"+i);
				data.setMapFlag(false);
				data.setData(JsonConvertUtils.toJson(info));
				boolean flag = transport.getElasticSession().addEsData(data);
				System.out.println("index="+i+",flag="+flag);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		logger.info("采用 transport 服务放数据 end !");
	}
}
