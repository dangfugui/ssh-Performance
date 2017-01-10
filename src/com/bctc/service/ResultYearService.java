package com.bctc.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bctc.entity.ResultYear;
import com.bctc.entity.User;
import com.bctc.entity.Year;
@Component
public class ResultYearService extends SuperService {
	public void save(ResultYear resultYear){
		hibernateTemplate.saveOrUpdate(resultYear);
	}
	
	/**
	 * 查找某人某年的总成绩
	 * @param year
	 * @param aimUser
	 * @return 
	 */
	public ResultYear load(Year year, User aimUser) {
		List<ResultYear> list = (List<ResultYear>) hibernateTemplate.find("from ResultYear where year.yid= ? and aimUser.uid = ?",
				new Object[]{year.getYid(),aimUser.getUid()});
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else {
			return null;
		}
	
	}
}
