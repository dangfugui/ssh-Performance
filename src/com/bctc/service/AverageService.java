package com.bctc.service;


import java.util.List;

import org.springframework.stereotype.Component;

import com.bctc.entity.Average;
import com.bctc.entity.Proportion;
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
	
	public List<Average> find(Year year,long aimUserid){
		@SuppressWarnings("unchecked")
		List<Average> list=(List<Average>) hibernateTemplate.find
				("from Average where year = ? and aimUser.uid = ? order by average desc ",
				new Object[]{year,aimUserid});
		return list;
	}
	
	/**
	 * 根据年度，用户id  来源用户id  获取评比例
	 * @param quarter 季度
	 * @param aimUserid	目标用户id
	 * @param sourceUserid	来源用户id
	 * @return 评分比例表
	 */
	public Average find(Year year,long aimUserid,long sourceUserid){
		@SuppressWarnings("unchecked")
		List<Average> list=(List<Average>) hibernateTemplate.find
				("from Average where year = ? and aimUser.uid = ? and sourceUser.uid = ?",
				new Object[]{year,aimUserid,sourceUserid});
		if(null==list||list.size()<1){
			return null;
		}
		return list.get(0);
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
	
	/**
	 *删除某人年度的 全部评分比例
	 * @param quarterId	年度
	 * @param aimUserId	目标用户id
	 */
	public void delete(Year year, User aimUser) {
		// TODO Auto-generated method stub
		hibernateTemplate.bulkUpdate("delete Average where year = ? and aimUser = ?",
				new Object[]{year,aimUser});
	}
	
	
}
