package com.bctc.action;


import net.sf.json.JSONObject;  

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.bctc.entity.Department;
import com.bctc.entity.User;
import com.bctc.service.DepartmentService;

public class DepartmentAction extends SuperAction {
	private static final long serialVersionUID = 1L;
	private DepartmentService departmentService;
	private Department department=new Department();
	List<Department> list;
	@Override
	public String execute() throws Exception {
		return list();
	}
	
	public String add(){
		if(!Tool.havePower("E3")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		if(Tool.isAdmin()){
			departmentService.asve(department);
		}
		return list();
	}
	public String delete(){
		if(!Tool.havePower("E3")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		if(Tool.isAdmin()){
			departmentService.delete(department );
		}
		return list();
	}
	
	public String update(){
		if(!Tool.havePower("E3")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		if(Tool.isAdmin()){
			departmentService.update(department);
		}
		return list();
	}
	public String list(){
		if(!Tool.havePower("E3")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		list = departmentService.list();
		return "department_list";
	}
	//在Action中以传统方式输出JSON数据
	/*public void listDepartmentUser() throws IOException{
		JSONObject jsonObject=new JSONObject();
		List<String> list=new ArrayList<String>();
		
		department=departmentService.load(department.getDid());
		for (User u : department.getUsers()) {
			list.add(u.getName());
			System.out.println(u.getName());
		}

        jsonObject.accumulate("list", list);  
        response.setHeader("Content-type", "text/html;charset=UTF-8");  
        response.setCharacterEncoding("UTF-8");
	    PrintWriter out;  
	    out = response.getWriter();  
	    out.println(jsonObject.toString());  
	    out.flush();  
	    out.close();  
	}*/
	//在Action中以Struts2的方式输出JSON数据
	public String listDepartmentUserJson(){
		JSONObject jsonObject=new JSONObject();
		List<User> userList=new ArrayList<User>();
		department=departmentService.load(department.getDid());
		for (User u : department.getUsers()) {
			User user=new User();
			user.setUid(u.getUid());
			user.setName(u.getName());
			userList.add(user);
		}
        jsonObject.accumulate("userList", userList);  
        request.setAttribute("json", jsonObject.toString());
		return "json";
	}

	@Resource
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<Department> getList() {
		return list;
	}
	public void setList(List<Department> list) {
		this.list = list;
	}
	
	
}
