package com.bctc.service;


import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bctc.entity.Quarter;
import com.bctc.tool.State;

@Component
public class QuarterService extends SuperService{
	

	public void save(Quarter quarter){
		quarter.setDate(new Date());
		hibernateTemplate.save(quarter);
		
	}
	
	public void delete(Quarter quarter){
		hibernateTemplate.bulkUpdate("update Quarter set state = ? where qid= ?", 
				new Object[]{State.DELETE,quarter.getQid()});
	}
	
	public void update(Quarter quarter){
		String hql="update Quarter set name=? where qid=?";
		hibernateTemplate.bulkUpdate(hql, new Object[]{quarter.getName(),quarter.getQid()});
	}
	
	public List<Quarter> list(){
		@SuppressWarnings("unchecked")
		List<Quarter> list = (List<Quarter>) hibernateTemplate.find("from Quarter where state in "+State.LISTIN+" order by date desc");
		return list;
	}
	public Quarter load(long id){
		@SuppressWarnings("unchecked")
		List<Quarter> list = (List<Quarter>) hibernateTemplate.find("from Quarter where qid= ?",new Object[]{id});
		return list.get(0);
		//return hibernateTemplate.load(Quarter.class, id);
	}
	
	
}
