package com.bctc.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bctc.entity.Department;
import com.bctc.entity.Role;
import com.bctc.entity.User;


public class UserServiceTest {
	UserService service ;
	@Before
	public void init(){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		service =(UserService)ctx.getBean("userService");	
	}
	@Test
	public void list(){
		List<User> list = service.list();
		for (User user : list) {
			System.out.println(user.getName());
		}
	}
	
	@Test
	public void add(){
		User user=new User("root1", "root1");
		/*Department dep=new Department("depart");
		dep.setDid(7);
		Role role=new Role("role", 5);
		role.setRid(5);
		user.setRole(role);
		user.setDepartment(dep);*/
		service.save(user);
	}
	@Test
	public void login(){
	//	Boolean list=service.login(new User("root", "root"));
		//System.out.println(list);
	}
}
