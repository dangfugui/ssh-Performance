package com.bctc.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bctc.entity.ContentKPI;
import com.bctc.entity.Quarter;
import com.bctc.entity.ScoreKPI;
import com.bctc.entity.User;

@Component
public class ScoreKPIService extends SuperService{

	
	public void save(ScoreKPI scoreKPI){
		hibernateTemplate.save(scoreKPI);
	}
	
	public void update(ScoreKPI scoreKPI){
		
		hibernateTemplate.update(scoreKPI);
	}
	
	
	public ScoreKPI load(long contentKPIid,long userId) {
		@SuppressWarnings("unchecked")
		List<ScoreKPI> list = (List<ScoreKPI>) hibernateTemplate.find("from ScoreKPI where contentKPI.cid=? and fillUser.uid = ?  ", 
				new Object[]{contentKPIid,userId});
		if(null==list||list.size()<1){
			return null;
		}
		return list.get(0);
	}

	public List<ScoreKPI> find(Quarter quarter, User aimUser, User sourceUser) {
		return (List<ScoreKPI>) hibernateTemplate.find("from ScoreKPI where fillUser.uid = ? and contentKPI.kpi.aimUser.uid = ? and contentKPI.kpi.quarter.qid= ?",
				new Object[]{sourceUser.getUid(),aimUser.getUid(),quarter.getQid()});
	}

	public ScoreKPI find(ContentKPI contentKPI,	User sourceUser) {
		List<ScoreKPI> list = (List<ScoreKPI>) hibernateTemplate.find("from ScoreKPI where contentKPI.cid=? and fillUser.uid = ? ",
				new Object[]{contentKPI.getCid(),sourceUser.getUid()});
		if(null==list||list.size()==0){
			return null;
		}	
		return list.get(0);
	}

}
