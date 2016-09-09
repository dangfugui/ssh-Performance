package com.bctc.service;


import java.util.List;

import org.springframework.stereotype.Component;

import com.bctc.entity.Department;
import com.bctc.tool.State;

@Component
public class DepartmentService extends SuperService {
	
	
	public void  asve(Department department) {
		hibernateTemplate.save(department);
	}

	public void delete(Department department){
		department=load(department.getDid());
		department.setState(State.DELETE);
		hibernateTemplate.update(department);
	}
	
	public void update(Department department){
		hibernateTemplate.bulkUpdate("update Department set name=? , state=? where did =? ", 
				new Object[]{department.getName(),department.getState(),department.getDid()});
		
	}
	public Department load(long id){
		return hibernateTemplate.load(Department.class, id);
	}
	@SuppressWarnings("unchecked")
	public List<Department> list(){
		return (List<Department>) hibernateTemplate.find("from Department where state in "+State.LISTIN+" order by did");
	}
	
	
}
