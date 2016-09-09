package com.bctc.service;


import java.util.List;

import org.hibernate.cfg.HbmBinder;
import org.springframework.stereotype.Component;

import com.bctc.entity.PowerGroup;
import com.bctc.tool.State;
@Component
public class PowerGroupService extends SuperService {
	
	public void save(PowerGroup powerGroup){
		hibernateTemplate.save(powerGroup);
	}
	public  void delete(PowerGroup powerGroup) {
		hibernateTemplate.bulkUpdate("update PowerGroup set state = ? where pid = ?",
				new Object[]{State.DELETE,powerGroup.getPid()});
	}
	public void update(PowerGroup powerGroup){
		hibernateTemplate.bulkUpdate("update PowerGroup set name=? ,powerString=? where pid = ?", 
				new Object[]{powerGroup.getName(),powerGroup.getPowerString(),powerGroup.getPid()});
	}
	public List<PowerGroup> list(){
		List<PowerGroup> list = (List<PowerGroup>) hibernateTemplate.find("from PowerGroup where state in "+State.LISTIN);
		return list;
	}
	public PowerGroup load(long pid) {
		return hibernateTemplate.load(PowerGroup.class, pid);
	}
	
}
