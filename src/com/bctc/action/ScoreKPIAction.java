package com.bctc.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import com.bctc.entity.ContentKPI;
import com.bctc.entity.KPI;
import com.bctc.entity.Proportion;
import com.bctc.entity.Quarter;
import com.bctc.entity.ScoreKPI;
import com.bctc.entity.User;
import com.bctc.service.KPIService;
import com.bctc.service.ProportionService;
import com.bctc.service.ScoreKPIService;
import com.bctc.tool.MySession;

public class ScoreKPIAction  extends SuperAction{
	private static final long serialVersionUID = 1L;
	private ScoreKPIService scoreKPIService;
	private Map<Integer, Double> map=new HashMap<Integer, Double>();
	private ScoreKPI scoreKPI=new ScoreKPI();
	private Quarter quarter=new Quarter();
	private ProportionService proportionService;
	private KPIService kpiService;
	private User user;
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	
	
	/**
	 * 保存给某人提交的得分
	 * @return
	 */
	public String saveScoreKPIs(){
		quarter=(Quarter) session.getAttribute(MySession.QUARTER);
		user=(User) session.getAttribute(MySession.LOGINUSER);
		KPI kpi = null;
		
		for (Entry<Integer, Double> entry : map.entrySet()) {
			System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
			int cid=entry.getKey();
			double score;
			if(null==entry.getValue()){
				score=0.0;
			}else{
				score=entry.getValue();
			}
			//sum+=score;
			scoreKPI=scoreKPIService.load(cid,user.getUid());
			if(null==scoreKPI){
				scoreKPI=new ScoreKPI();
				scoreKPI.setScore(score);
				scoreKPI.setFillUser(user);
				ContentKPI contentKPI= kpiService.loadContent(cid);
				kpi=contentKPI.getKpi();
				scoreKPI.setContentKPI(contentKPI);
				scoreKPIService.save(scoreKPI);
			}else {
				scoreKPI.setScore(score);
				kpi=scoreKPI.getContentKPI().getKpi();
				scoreKPIService.update(scoreKPI);
			}
		}
		List<ScoreKPI> scoreKPIList = scoreKPIService.find(quarter,kpi.getAimUser(),user);			
		double sum = 0;
		if(scoreKPIList!=null){
			for(ScoreKPI s:scoreKPIList){
				sum+=s.getScore();
			}
		}
		Proportion proportion = proportionService.find(quarter, kpi.getAimUser().getUid(), user.getUid());
		if(sum < 0.000001){
			proportion.setState("未打分");
		}else{
			proportion.setState(""+sum);
		}
		proportionService.update(proportion);
		this.addActionMessage("打分成功");
		return "success";//跳转到登录页面
	}


	public Map<Integer, Double> getMap() {
		return map;
	}
	
	public void setMap(Map<Integer, Double> map) {
		this.map = map;
	}


	public ScoreKPI getScoreKPI() {
		return scoreKPI;
	}


	public void setScoreKPI(ScoreKPI scoreKPI) {
		this.scoreKPI = scoreKPI;
	}

	@Resource
	public void setScoreKPIService(ScoreKPIService scoreKPIService) {
		this.scoreKPIService = scoreKPIService;
	}

	public Quarter getQuarter() {
		return quarter;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setQuarter(Quarter quarter) {
		this.quarter = quarter;
	}

	public KPIService getKpiService() {
		return kpiService;
	}
	@Resource
	public void setKpiService(KPIService kpiService) {
		this.kpiService = kpiService;
	}

	public ProportionService getProportionService() {
		return proportionService;
	}
	@Resource
	public void setProportionService(ProportionService proportionService) {
		this.proportionService = proportionService;
	}
	
	
}
