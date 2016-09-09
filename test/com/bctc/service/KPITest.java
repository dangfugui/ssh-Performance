package com.bctc.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bctc.entity.Department;
import com.bctc.entity.KPI;
import com.bctc.entity.Quarter;
import com.bctc.entity.User;

public class KPITest {
	KPIService service;
	@Before
	public void init(){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		service =(KPIService)ctx.getBean("kPIService");	
	}
	@Test
	public void add(){
		KPI kpi=new KPI();
		User aim=new User();
		aim.setUid(1);;
		User from=new User();
		from.setUid(2);
		Quarter quarter = new Quarter();
		quarter.setQid(1);
		kpi.setQuarter(quarter);
		kpi.setAimUser(aim);
		kpi.setFillUser(from);
		service.saveOrUpdate(kpi);
	}
}
