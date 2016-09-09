package com.bctc.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bctc.entity.Department;

public class DepartmentServiceTest {
	DepartmentService service;
	@Before
	public void init(){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		service =(DepartmentService)ctx.getBean("departmentServict");	
	}
	@Test
	public void add(){
		service.asve(new Department("dep1"));;
	}
	@Test
	public void list(){
		List<Department> list = service.list();
		for (Department department : list) {
			System.out.println(department.toString());
		}
	}
}
