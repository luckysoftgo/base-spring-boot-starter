package com.application.boot.test.kylin;

import com.application.base.kylin.jdbc.factory.KylinJdbcSessionFactory;
import com.application.base.kylin.rest.factory.KylinJestSessionFactory;
import com.application.boot.kylin.core.KylinConfigProperties;
import com.application.boot.test.BasicStartTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author : 孤狼
 * @NAME: ElasticStartTest
 * @DESC: es 测试
 **/
public class KylinStartTest extends BasicStartTest {
	
	@Autowired
	private KylinConfigProperties kylinConfigProperties;
	
	@Autowired
	private KylinJdbcSessionFactory jdbcSessionFactory;
	
	@Autowired
	private KylinJestSessionFactory jestSessionFactory;
	
	@Test
	public void testJdbc(){
		String sql = "SELECT MEMBER_ID,TERM,GRADE,SUB_GRADE,ZIP_CODE,ADDR_STATE,PROCESSING_DTTM , COUNT(1) AS _COUNT_,SUM(LOAN_AMNT) AS SUM_LOAN_AMNT,SUM(FUNDED_AMNT) AS SUM_FUNDED_AMNT,SUM(FUNDED_AMNT_INV) AS SUM_FUNDED_AMNT_INV,MAX(LOAN_AMNT) AS MAX_LOAN_AMNT,MAX(FUNDED_AMNT) AS MAX_FUNDED_AMNT,MAX(INT_RATE) AS MAX_INT_RATE,MAX(FUNDED_AMNT_INV) AS MAX_FUNDED_AMNT_INV,MIN(LOAN_AMNT) AS MIN_LOAN_AMNT,MIN(FUNDED_AMNT) AS MIN_FUNDED_AMNT,MIN(INT_RATE) AS MIN_INT_RATE,MIN(FUNDED_AMNT_INV) AS MIN_FUNDED_AMNT_INV,PERCENTILE_APPROX(INT_RATE,0.5) AS PERCENTILE_INT_RATE FROM USE_AND_DROP.CREDIT_RISK_DATA GROUP BY MEMBER_ID,TERM,GRADE,SUB_GRADE,ZIP_CODE,ADDR_STATE,PROCESSING_DTTM limit 3";
		LinkedList<Map<String, Object>> datas = jdbcSessionFactory.getJdbcSession().selectSQL("Kkklin",sql);
		System.out.println("得到的数据的集合大小是:"+datas.size());
	}
	
	@Test
	public void testRest(){
		String json = jestSessionFactory.getRestSession().queryTables("Kkklin");
		System.out.println("得到的结果数据是:"+json);
	}
	
}
