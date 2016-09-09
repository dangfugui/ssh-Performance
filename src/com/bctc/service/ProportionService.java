package com.bctc.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bctc.entity.Proportion;
import com.bctc.entity.Quarter;

@Component
public class ProportionService extends SuperService {
	
	
	public void save(Proportion proportion) {
		hibernateTemplate.save(proportion);	
	}
	/*public void save(int quarterId,int aimUserID,int SourceUserId,Double proportion) {
		// TODO Auto-generated method stub
		
	}*/
	/**
	 *删除某人某个季度的 全部评分比例
	 * @param quarterId	季度id
	 * @param aimUserId	目标用户id
	 */
	public void delete(long quarterId, long aimUserId) {
		// TODO Auto-generated method stub
		hibernateTemplate.bulkUpdate("delete Proportion where quarter.qid = ? and aimUser.uid = ?",
				new Object[]{quarterId,aimUserId});
	}
	
	/**
	 * 删除
	 */
	public void delete(Proportion proportion) {
		hibernateTemplate.delete(proportion);
	}

	
	public void update(Proportion proportion) {
		hibernateTemplate.update(proportion);
	}
	
	/**
	 * 根据季度，用户id  来源用户id  获取评分表
	 * @param quarter 季度
	 * @param aimUserid	目标用户id
	 * @param sourceUserid	来源用户id
	 * @return 评分比例表
	 */
	public Proportion find(Quarter quarter,long aimUserid,long sourceUserid){
		@SuppressWarnings("unchecked")
		List<Proportion> list=(List<Proportion>) hibernateTemplate.find
				("from Proportion where quarter = ? and aimUser.uid = ? and sourceUser.uid = ?",
				new Object[]{quarter,aimUserid,sourceUserid});
		if(null==list||list.size()<1){
			return null;
		}
		return list.get(0);
	}
	/**
	 * 根据季度 目标id 返回某人的评分比例
	 * @param quarter
	 * @param aimUserid
	 * @return
	 */
	public List<Proportion> find(Quarter quarter,long aimUserid){
		@SuppressWarnings("unchecked")
		List<Proportion> list=(List<Proportion>) hibernateTemplate.find
				("from Proportion where quarter = ? and aimUser.uid = ? order by proportion desc ",
				new Object[]{quarter,aimUserid});
		return list;
	}
	/**
	 * 根据季度  和我的 id   返回我要打分的 评分表
	 * @param quarter
	 * @param sourceUserid
	 * @return
	 */
	public List<Proportion> findMyFill(Quarter quarter,long sourceUserid){
		@SuppressWarnings("unchecked")
		List<Proportion> list=(List<Proportion>) hibernateTemplate.find
				("from Proportion where quarter = ? and sourceUser.uid = ? order by proportion desc ",
				new Object[]{quarter,sourceUserid});
		return list;
	}
	

	

	

	

	
}
