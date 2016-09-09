package com.bctc.service;


import java.util.List;

import org.springframework.stereotype.Component;

import com.bctc.entity.Average;
import com.bctc.entity.Quarter;
import com.bctc.entity.User;
import com.bctc.entity.Year;


@Component
public class AverageService  extends SuperService{
	
	
	public void save(Average average){
		hibernateTemplate.save(average);
	}
	/**
	 * 通过季度和登录用户，查询出需要打分的列别
	 * @param quarter
	 * @param user
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public List<Average> find(Year year, User user) {
		return (List<Average>) hibernateTemplate.find("from Average where year.yid = ? and sourceUser.uid = ?",
				new Object[]{year.getYid(),user.getUid()});
	}
	
	public List<Average> findByAimUser(Year year, User aimUser){
		return (List<Average>) hibernateTemplate.find("from Average where year.yid = ? and aimUser.uid = ?",
				new Object[]{year.getYid(),aimUser.getUid()});
	}
	
	/**
	 * 加载某个average
	 * @param aid
	 */
	public Average load(long aid) {
		return hibernateTemplate.load(Average.class, aid);
	}
	public void update(Average average) {
		hibernateTemplate.bulkUpdate("update Average set average=? , total=? ,state=? where aid=?", 
				new Object[]{average.getAverage(),average.getTotal(),average.getState(),average.getAid()});
	}

	
}
