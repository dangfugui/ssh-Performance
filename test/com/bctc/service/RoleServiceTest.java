package com.bctc.service;

import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RoleServiceTest {
	RoleService service;
	@Before
	public void init(){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		service =(RoleService)ctx.getBean("RoleService");	
	}
	//public void add
}
