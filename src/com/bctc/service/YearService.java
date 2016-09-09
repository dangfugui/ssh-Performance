package com.bctc.service;




import java.util.List;

import org.springframework.stereotype.Component;

import com.bctc.entity.Behavior;
import com.bctc.entity.Year;
import com.bctc.tool.State;
@SuppressWarnings("unchecked")
@Component
public class YearService  extends SuperService{
	public long save(Year year){
		return  (Long) hibernateTemplate.save(year);
	}
	
	public void saveBehavior(Behavior behavior){
		hibernateTemplate.save(behavior);
	}

	public void delete(Year year) {
		year=load(year.getYid());
		year.setState(State.DELETE);
		hibernateTemplate.update(year);
	}
	
	public void delete(Behavior behavior) {
		hibernateTemplate.update(behavior);
	}
	
	public List<Year> list() {
		return (List<Year>) hibernateTemplate.find("from Year where state in "+State.LISTIN + " order by date desc ");
	}
	
	public Year load(Long id){
		return hibernateTemplate.load(Year.class, id);
	}
}
