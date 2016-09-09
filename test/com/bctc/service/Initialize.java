package com.bctc.service;

import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bctc.entity.Role;
import com.bctc.entity.User;

public class Initialize {

	private UserService userService;
	private RoleService roleService;
	@Before
	public void init(){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		userService =(UserService)ctx.getBean("userService");	
		roleService=(RoleService) ctx.getBean("roleService");
	}
	public void start() {
		//userService.save(new User("root","root") );
		roleService.save(new Role());
	}
}
