package com.bctc.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bctc.entity.Behavior;
import com.bctc.entity.ScoreYear;
import com.bctc.entity.User;
import com.bctc.entity.Year;

@Component
public class ScoreYearService extends SuperService {

	public void save(ScoreYear scoreYear) {
		hibernateTemplate.save(scoreYear);
	}
	/**
	 * 根据行为绩效behavior 和填表人，目标人 获得  scoreYear
	 * @param behavior
	 * @param user
	 * @param user2
	 */
	public ScoreYear load(Behavior behavior, User fillUser, User aimUser) {
		List<ScoreYear> list = (List<ScoreYear>) hibernateTemplate.find("from ScoreYear where behavior.bid = ? and fillUser.uid = ? and aimUser.uid = ? ",
				new Object[]{behavior.getBid(),fillUser.getUid(),aimUser.getUid()});
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else {
			return null;
		}
	}
	public void update(ScoreYear scoreYear) {
		//hibernateTemplate.bulkUpdate("update Scou", values)
		hibernateTemplate.update(scoreYear);
	}
	/**
	 * 根据年度，填表人，被填表人查找scoreYear评分记录表内容
	 * @param year
	 * @param aimUser
	 * @param sourceUser
	 * @return 
	 */
	public List<ScoreYear> find(Year year, User aimUser, User sourceUser) {
		return 
		(List<ScoreYear>) hibernateTemplate.find("from ScoreYear where aimUser.uid=? and fillUser.uid = ? and behavior.year.yid=? ", 
				new Object[]{aimUser.getUid(),sourceUser.getUid(),year.getYid()});
	}
}
