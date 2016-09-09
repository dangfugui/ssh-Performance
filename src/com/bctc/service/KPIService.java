package com.bctc.service;


import java.util.List;

import org.springframework.stereotype.Component;

import com.bctc.entity.ContentKPI;
import com.bctc.entity.KPI;
import com.bctc.entity.Quarter;
import com.bctc.entity.User;
import com.bctc.tool.State;
@Component("kpiService")
public class KPIService extends SuperService{
	/**
	 * 添加kpi
	 * @param kpi
	 */
	/*public void save1(KPI kpi){
		hibernateTemplate.save(kpi);
	}*/
	public void saveOrUpdate(KPI kpi){
		hibernateTemplate.saveOrUpdate(kpi);
	}
	
	
	
	public void update(KPI kpi){
		hibernateTemplate.bulkUpdate("update KPI set fillUser=? ,result = ? where kid=? ", 
				new Object[]{kpi.getFillUser(),kpi.getResult(),kpi.getKid()});
	}
	/**
	 * 添加或修改KPI内容
	 * @param contentKPI
	 */
	public void saveOrUpdateContent(ContentKPI contentKPI) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(contentKPI);
	}
	
	public void delete(ContentKPI contentKPI){
		hibernateTemplate.delete(contentKPI);
	}
	
	public void delete(KPI kpi){
		hibernateTemplate.delete(kpi);
	}
	
	public ContentKPI loadContent(long id){
		return hibernateTemplate.load(ContentKPI.class, id);
	}
	
	/**
	 * 通过季度 和用户 加载KPI表
	 * @param quarter	季度
	 * @param user		用户
	 * @return	KPI
	 */
	public KPI load(Quarter quarter,User user){
		@SuppressWarnings("unchecked")
		List<KPI> kpis=(List<KPI>) hibernateTemplate.find("from KPI where quarter_qid=? and aimUser_uid=?  and state in "+State.LISTIN,
				new Object[]{quarter.getQid(),user.getUid()});
		if(null==kpis||kpis.size()<1){
			return null;
		}
		return  kpis.get(0);
	}
	public KPI load(long kid){
		return hibernateTemplate.load(KPI.class, kid);
	}
	public List<KPI> listKPI(long quarterId,int fillUserId) {
	
		@SuppressWarnings("unchecked")
		List<KPI> kpis=(List<KPI>) hibernateTemplate.find("from KPI where quarter.qid = ? and fillUser.uid = ?  and state in "+State.LISTIN,
				new Object[]{quarterId,fillUserId});
		return  kpis;
	}
	
	public List<ContentKPI> findContentKPIs(long userId, long quarterId) {
		@SuppressWarnings("unchecked")
		List<ContentKPI> list=(List<ContentKPI>) hibernateTemplate.find("from ContentKPI where sourceUser.uid = ? and kpi.quarter.qid = ? and kpi.state= ? order by date",
				new Object[]{userId,quarterId,"default"} );
		return list;
	}
	/**
	 * 查看某人  的指标模板
	 * @param user 用户
	 */
	public List<KPI> listTemplate(User user) {
		@SuppressWarnings("unchecked")
		List<KPI> list = (List<KPI>) hibernateTemplate.find("from KPI where aimUser.uid = ? and fillUser.uid = ? and  state <> ? order by date", new Object[]{user.getUid(),user.getUid(),"default"});
		return list;
	}

	public void save(ContentKPI contentKPI) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(contentKPI);
	}



	public void update(ContentKPI contentKPI) {
		hibernateTemplate.bulkUpdate("update ContentKPI set result=? where cid=? ", 
				new Object[]{contentKPI.getResult(),contentKPI.getCid()});
	}



	public List<KPI> getMyFillKPI(User user, Quarter quarter) {
		List<KPI> list = (List<KPI>) hibernateTemplate.find("from KPI where quarter.qid = ? and fillUser.uid = ? order by date", new Object[]{quarter.getQid(),user.getUid()});
		return list;
	}
	
	
}
