package com.bctc.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bctc.entity.Role;
import com.bctc.tool.State;
@Component
public class RoleService extends SuperService {
	
	public void save(Role role){
		hibernateTemplate.save(role);
	}	
	
	public void delete(Role role){
		role=load(role.getRid());
		role.setState(State.DELETE);
		hibernateTemplate.update(role);
	}
	
	public void update(Role role){
		hibernateTemplate.bulkUpdate("update Role set grade=? ,name=? , state=? where rid=? ",
				new Object[]{role.getGrade(),role.getName(),role.getState(),role.getRid()});
	}
	public Role load(long id){
		return hibernateTemplate.load(Role.class, id);
	}
	@SuppressWarnings("unchecked")
	public List<Role> list(){
		return (List<Role>) hibernateTemplate.find("from Role where  state in "+State.LISTIN+" order by grade");
	}
	/**
	 * 查找 比自己小的角色
	 * @param grade
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Role> listByGrade(int grade) {
		return (List<Role>) hibernateTemplate.find("from Role where grade > ?",new Object[]{grade});
	}
}
