package com.bctc.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.bctc.entity.Department;
import com.bctc.entity.User;
import com.bctc.tool.MD5Util;
import com.bctc.tool.State;
@SuppressWarnings("unchecked")
@Component
public class UserService extends SuperService {

	public void save(User user){
		user.setPassword(MD5Util.getMD5(user.getPassword()));
		hibernateTemplate.save(user);
	}
	/*public void save111(User user, int departmentDid, int roleListRid) {
		System.out.println(user.toString());
		hibernateTemplate.find("insert into User(name,password,department,role) value(?,?,?,?)", 
				new Object[]{user.getName(),user.getPassword(),departmentDid,roleListRid}
				);
	}*/
	public void delete(User user){
		user=load(user.getUid());
		user.setState(State.DELETE);
		hibernateTemplate.update(user);
	}
	public void update(User user){
		user.setPassword(MD5Util.getMD5(user.getPassword()));
		hibernateTemplate.bulkUpdate("update User set agentId=?, name=? , password = ? ,department.did=? , role.rid=? ,powerGroup.pid=? where uid=?",
					new Object[]{user.getAgentId(),user.getName(),user.getPassword(),user.getDepartment().getDid(),user.getRole().getRid(),user.getPowerGroup().getPid(),user.getUid()});
		//hibernateTemplate.update(user);
	}
	
	
	public User login(User user){
		if(null==user.getName()||user.getName().length()<1){
			return null;
		}
		//user.setPassword(MD5Util.getMD5(user.getPassword()));
		List<User> list = (List<User>) hibernateTemplate.find("from User where name = ? and state in "+State.LISTIN,
				new Object[]{user.getName()});
		if(null==list||list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public User load(long id){
		return hibernateTemplate.load(User.class, id);
	}
	

	public List<User> list(){
		return (List<User>) hibernateTemplate.find("from User where state in "+State.LISTIN+" order by agentId");
	}
	/**
	 * 根据姓名 部门 和 角色 模糊查找用户
	 * @param name 姓名
	 * @param departmentDid	部门id
	 * @param roleRid	角色id
	 * @return 用户列表
	 */
	public List<User> list(String name, long departmentDid, long roleRid) {
		if(null==name||name.length()<1){
			name="";
		}
		name="%"+name+"%";
		System.out.println(name);
		List<User> list=new ArrayList<User>();
		if(departmentDid!=0&&roleRid==0){
			list = (List<User>) hibernateTemplate.find("from User where name like ? and department_did=? and state in "+State.LISTIN+" order by agentId", 
					new Object[]{name,departmentDid});
		}else if(departmentDid==0&&roleRid!=0){
			list = (List<User>) hibernateTemplate.find("from User where name like ? and role_rid=? and state in "+State.LISTIN+" order by agentId", 
					new Object[]{name,roleRid});
		}else if(departmentDid!=0&&roleRid!=0){
			list = (List<User>) hibernateTemplate.find("from User where name like ? and department_did=? and role_rid=? and state in "+State.LISTIN+" order by agentId", 
					new Object[]{name,departmentDid,roleRid});
		}else {
			list = (List<User>) hibernateTemplate.find("from User where name like ? and state in "+State.LISTIN+" order by agentId", 
					new Object[]{name});
		}
		return list;
	}
	
	public List<User> listExcludeUser(long userId, long departmentDid, long roleRid) {
	
		List<User> list=new ArrayList<User>();
		if(departmentDid!=0&&roleRid==0){
			list = (List<User>) hibernateTemplate.find("from User where uid <> ? and department_did=? and state in "+State.LISTIN+" order by agentId", 
					new Object[]{userId,departmentDid});
		}else if(departmentDid==0&&roleRid!=0){
			list = (List<User>) hibernateTemplate.find("from User where  uid <> ? and role_rid=? and state in "+State.LISTIN+" order by agentId", 
					new Object[]{userId,roleRid});
		}else if(departmentDid!=0&&roleRid!=0){
			list = (List<User>) hibernateTemplate.find("from User where  uid <> ? and department_did=? and role_rid=? and state in "+State.LISTIN+" order by agentId", 
					new Object[]{userId,departmentDid,roleRid});
		}else {
			list = (List<User>) hibernateTemplate.find("from User where uid <> ? and state in "+State.LISTIN+" order by agentId", 
					new Object[]{userId});
		}
		return list;
	}
	//public List<User> listExcludeDepartment(long , long departmentDid, long roleRid) {}
	public List<User> listExcludeDepartment(Department department,String name, long departmentDid, long roleRid) {
		if(null==name||name.length()<1){
			name="";
		}
		name="%"+name+"%";
		System.out.println(name);
		List<User> list=new ArrayList<User>();
		if(departmentDid!=0&&roleRid==0){
			list = (List<User>) hibernateTemplate.find("from User where department.did <> ? and name like ? and department_did=? and state in "+State.LISTIN+" order by agentId", 
					new Object[]{department.getDid(),name,departmentDid});
		}else if(departmentDid==0&&roleRid!=0){
			list = (List<User>) hibernateTemplate.find("from User where department.did <> ? and  name like ? and role_rid=? and state in "+State.LISTIN+" order by agentId", 
					new Object[]{department.getDid(),name,roleRid});
		}else if(departmentDid!=0&&roleRid!=0){
			list = (List<User>) hibernateTemplate.find("from User where department.did <> ? and  name like ? and department_did=? and role_rid=? and state in "+State.LISTIN+" order by agentId", 
					new Object[]{department.getDid(),name,departmentDid,roleRid});
		}else {
			list = (List<User>) hibernateTemplate.find("from User where  department.did <> ? and name like ? and state in "+State.LISTIN+" order by agentId", 
					new Object[]{department.getDid(),name});
		}
		return list;
	}

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	public List<User> listByGrade(String name, long departmentDid, long roleRid,
			int grade) {
		if(null==name||name.length()<1){
			name="";
		}
		System.out.println(name);
		name="%"+name+"%";
		List<User> list=new ArrayList<User>();
		if(departmentDid!=0&&roleRid==0){
			list = (List<User>) hibernateTemplate.find("from User where name like ? and department_did=? and state in "+State.LISTIN+" and role.grade > ?  order by agentId", 
					new Object[]{name,departmentDid,grade});
		}else if(departmentDid==0&&roleRid!=0){
			list = (List<User>) hibernateTemplate.find("from User where name like ? and role_rid=? and state in "+State.LISTIN+" and role.grade > ?  order by agentId", 
					new Object[]{name,roleRid,grade});
		}else if(departmentDid!=0&&roleRid!=0){
			list = (List<User>) hibernateTemplate.find("from User where name like ? and department_did=? and role_rid=? and state in "+State.LISTIN+" and role.grade > ?  order by agentId", 
					new Object[]{name,departmentDid,roleRid,grade});
		}else {
			list = (List<User>) hibernateTemplate.find("from User where name like ? and state in "+State.LISTIN+" and role.grade > ?  order by agentId", 
					new Object[]{name,grade});
		}
		return list;
	}
	
	
	
}
