package com.bctc.action;

import java.util.List;

import javax.annotation.Resource;

import com.bctc.entity.Role;
import com.bctc.service.RoleService;

public class RoleAction extends SuperAction {
	private static final long serialVersionUID = 1L;
	private RoleService roleService;
	private Role role=new Role();
	private List<Role> list;	
	public String execute() throws Exception {
		return SUCCESS;
	}
	public String add(){
		if(!Tool.havePower("E4")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		if(Tool.isAdmin()){
			roleService.save(role);
		}
		return list();
	}
	
	public String delete(){
		if(!Tool.havePower("E4")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		if(Tool.isAdmin()){
			roleService.delete(role);
		}
		return list();
	}
	
	public String update(){
		if(!Tool.havePower("E4")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		if(Tool.isAdmin()){
			roleService.update(role);
		}
		return list();
	}
	
	public String list(){
		if(!Tool.havePower("E4")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		list=roleService.list();
		return "role_list";
	}
	
	
	@Resource
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public List<Role> getList() {
		return list;
	}
	public void setList(List<Role> list) {
		this.list = list;
	}
	
	
}
